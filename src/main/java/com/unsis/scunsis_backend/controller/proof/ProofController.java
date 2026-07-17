package com.unsis.scunsis_backend.controller.proof;

import com.lowagie.text.Document;
import com.lowagie.text.Image;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.PdfWriter;
import com.unsis.scunsis_backend.dto.request.proof.CanvasPdfRequest;
import com.unsis.scunsis_backend.dto.request.proof.ProofRequest;
import com.unsis.scunsis_backend.dto.response.proof.CanvasPdfResponse;
import com.unsis.scunsis_backend.dto.response.proof.ProofBulkResponse;
import com.unsis.scunsis_backend.dto.response.proof.ProofResponse;
import com.unsis.scunsis_backend.model.enums.EParticipationRole;
import com.unsis.scunsis_backend.model.receiver.Receiver;
import com.unsis.scunsis_backend.service.excel.ExcelService;
import com.unsis.scunsis_backend.service.excel.ExcelService.ParticipantRow;
import com.unsis.scunsis_backend.service.proof.ProofService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

@RestController
@RequestMapping("/api/v1/proof")
@RequiredArgsConstructor
public class ProofController {

    private final ProofService proofService;
    private final ExcelService excelService;

    @Value("${app.pdf.generated-dir}")
    private String generatedDir;

    @PreAuthorize("hasAnyRole('ADMIN', 'CAPTURISTA')")
    @PostMapping("/generate-pdfs")
    public ResponseEntity<CanvasPdfResponse> generatePdfs(@RequestBody CanvasPdfRequest request) {
        try {
            String base64Data = request.getCanvasImage();
            if (base64Data.contains(",")) {
                base64Data = base64Data.split(",")[1];
            }
            byte[] imageBytes = Base64.getDecoder().decode(base64Data);

            BufferedImage bufferedImage = ImageIO.read(new ByteArrayInputStream(imageBytes));
            if (bufferedImage == null) {
                return ResponseEntity.badRequest().build();
            }
            int width = bufferedImage.getWidth();
            int height = bufferedImage.getHeight();

            Path outputDir = Paths.get(generatedDir).toAbsolutePath().normalize();
            Files.createDirectories(outputDir);

            List<List<String>> persons = request.getData();
            int count = 0;
            for (List<String> person : persons) {
                String nombre = person.size() > 0 ? person.get(0) : "";
                String primerApellido = person.size() > 1 ? person.get(1) : "";
                String segundoApellido = person.size() > 2 ? person.get(2) : "";
                String nombreCompleto = String.join(" ",
                        List.of(nombre, primerApellido, segundoApellido).stream()
                                .filter(s -> s != null && !s.isBlank())
                                .toArray(String[]::new));

                String filename = "constancia-" + nombreCompleto.replaceAll("\\s+", "_") + ".pdf";
                Path filePath = outputDir.resolve(filename);

                try (OutputStream os = Files.newOutputStream(filePath)) {
                    Document document = new Document(new Rectangle(width, height), 0, 0, 0, 0);
                    PdfWriter.getInstance(document, os);
                    document.open();
                    Image pdfImage = Image.getInstance(imageBytes);
                    pdfImage.scaleToFit(width, height);
                    pdfImage.setAbsolutePosition(0, 0);
                    document.add(pdfImage);
                    document.close();
                }
                count++;
            }

            return ResponseEntity.ok(CanvasPdfResponse.builder()
                    .count(count)
                    .path(outputDir.toString())
                    .build());
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'CAPTURISTA')")
    @GetMapping
    public ResponseEntity<List<ProofResponse>> getAll() {
        return ResponseEntity.ok(proofService.getAll());
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'CAPTURISTA')")
    @GetMapping("/{folio}")
    public ResponseEntity<ProofResponse> getById(@PathVariable String folio) {
        return ResponseEntity.ok(proofService.getById(folio));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<ProofResponse> createProof(@RequestBody ProofRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(proofService.createProof(request));
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'CAPTURISTA')")
    @GetMapping("/by-activity/{activityId}")
    public ResponseEntity<List<ProofResponse>> getByActivity(@PathVariable Long activityId) {
        return ResponseEntity.ok(proofService.getByActivity(activityId));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{folio}")
    public ResponseEntity<Void> deleteProof(@PathVariable String folio) {
        proofService.deleteById(folio);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'CAPTURISTA')")
    @GetMapping("/{folio}/pdf")
    public ResponseEntity<byte[]> downloadPdf(@PathVariable String folio) {
        byte[] pdfBytes = proofService.generatePdf(folio);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData("attachment", "constancia_" + folio + ".pdf");
        return ResponseEntity.ok().headers(headers).body(pdfBytes);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'CAPTURISTA')")
    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ProofBulkResponse> uploadExcel(
            @RequestParam("file") MultipartFile file,
            @RequestParam("eventId") Long eventId,
            @RequestParam("activityId") Long activityId,
            @RequestParam("senderId") Long senderId,
            @RequestParam(value = "role", required = false) String role
    ) {
        try {
            EParticipationRole defaultRole = role != null
                    ? EParticipationRole.valueOf(role.trim().toUpperCase())
                    : null;

            List<ParticipantRow> rows = excelService.parseParticipants(file, defaultRole);

            List<String> rowErrors = rows.stream()
                    .filter(r -> r.error() != null)
                    .map(r -> r.receiver().getName() + " " + r.receiver().getLastName() + ": " + r.error())
                    .toList();

            List<ParticipantRow> validRows = rows.stream()
                    .filter(r -> r.error() == null)
                    .toList();

            if (validRows.isEmpty()) {
                List<String> allErrors = new ArrayList<>(rowErrors);
                if (allErrors.isEmpty()) {
                    allErrors.add("No se encontraron participantes validos en el archivo. " +
                            "Asegurate de que las columnas NOMBRE y PRIMERAPELLIDO existan.");
                }
                return ResponseEntity.badRequest().body(
                        ProofBulkResponse.builder()
                                .totalRows(rows.size())
                                .successCount(0)
                                .errorCount(allErrors.size())
                                .errors(allErrors)
                                .build()
                );
            }

            List<Receiver> receivers = validRows.stream()
                    .map(ParticipantRow::receiver)
                    .toList();

            List<EParticipationRole> roles = validRows.stream()
                    .map(ParticipantRow::role)
                    .toList();

            ProofBulkResponse response = proofService.createProofsBulk(
                    eventId, activityId, senderId, receivers, roles
            );

            if (!rowErrors.isEmpty()) {
                response.getErrors().addAll(0, rowErrors);
                response.setErrorCount(response.getErrorCount() + rowErrors.size());
                response.setTotalRows(response.getTotalRows() + rowErrors.size());
            }

            return ResponseEntity.ok(response);
        } catch (IOException e) {
            return ResponseEntity.badRequest().body(
                    ProofBulkResponse.builder()
                            .totalRows(0)
                            .successCount(0)
                            .errorCount(1)
                            .errors(List.of("Error al leer el archivo: " + e.getMessage()))
                            .build()
            );
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(
                    ProofBulkResponse.builder()
                            .totalRows(0)
                            .successCount(0)
                            .errorCount(1)
                            .errors(List.of("Rol invalido. Usa: PONENTE, PARTICIPANTE, ORGANIZADOR o RECONOCIMIENTO"))
                            .build()
            );
        }
    }
}
