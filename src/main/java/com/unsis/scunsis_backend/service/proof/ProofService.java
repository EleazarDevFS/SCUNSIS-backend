package com.unsis.scunsis_backend.service.proof;

import com.lowagie.text.Document;
import com.lowagie.text.Image;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.PdfWriter;
import com.unsis.scunsis_backend.dto.request.proof.CanvasPdfRequest;
import com.unsis.scunsis_backend.dto.request.proof.ProofRequest;
import com.unsis.scunsis_backend.dto.response.proof.CanvasPdfResponse;
import com.unsis.scunsis_backend.dto.response.proof.ProofBulkResponse;
import com.unsis.scunsis_backend.dto.response.proof.ProofResponse;
import com.unsis.scunsis_backend.exception.AppException;
import com.unsis.scunsis_backend.mapper.proof.ProofMapper;
import com.unsis.scunsis_backend.model.activity.Activity;
import com.unsis.scunsis_backend.model.enums.EParticipationRole;
import com.unsis.scunsis_backend.model.event.Event;
import com.unsis.scunsis_backend.model.proof.Proof;
import com.unsis.scunsis_backend.model.proof.ProofFile;
import com.unsis.scunsis_backend.model.receiver.Receiver;
import com.unsis.scunsis_backend.model.sender.Sender;
import com.unsis.scunsis_backend.repository.activity.IActivityRepository;
import com.unsis.scunsis_backend.repository.event.IEventRepository;
import com.unsis.scunsis_backend.repository.proof.IProofFileRepository;
import com.unsis.scunsis_backend.repository.proof.IProofRepository;
import com.unsis.scunsis_backend.repository.receiver.IReceiverRepository;
import com.unsis.scunsis_backend.repository.sender.ISenderRepository;
import com.unsis.scunsis_backend.service.excel.ExcelService;
import com.unsis.scunsis_backend.service.pdf.PdfGenerationService;
import com.unsis.scunsis_backend.util.FolioGenerator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;
import java.io.IOException;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProofService {

    private final IProofRepository proofRepository;
    private final IProofFileRepository proofFileRepository;
    private final ProofMapper proofMapper;
    private final FolioGenerator folioGenerator;
    private final PdfGenerationService pdfGenerationService;
    private final ISenderRepository senderRepository;
    private final IReceiverRepository receiverRepository;
    private final IActivityRepository activityRepository;
    private final IEventRepository eventRepository;
    private final ExcelService excelService;

    private static final String PROOF_NOT_FOUND = "Constancia no encontrada con folio: ";
    private static final String SENDER_NOT_FOUND = "Emisor no encontrado";
    private static final String ACTIVITY_NOT_FOUND = "Actividad no encontrada";
    private static final String EVENT_NOT_FOUND = "Evento no encontrado";

    @Value("${app.pdf.generated-dir}")
    private String generatedDir;

    public List<ProofResponse> getAll() {
        return proofMapper.toDtos(proofRepository.findAll());
    }

    public ProofResponse getById(String folio) {
        Proof proof = proofRepository.findById(folio)
                .orElseThrow(() -> new AppException(PROOF_NOT_FOUND + folio, HttpStatus.NOT_FOUND));
        return proofMapper.toDto(proof);
    }

    @Transactional
    public void deleteById(String folio) {
        Proof proof = proofRepository.findById(folio)
                .orElseThrow(() -> new AppException(PROOF_NOT_FOUND + folio, HttpStatus.NOT_FOUND));

        proofFileRepository.findByFolio(folio).ifPresent(pf -> {
            try {
                Path filePath = Paths.get(pf.getRutaPdf());
                Files.deleteIfExists(filePath);
            } catch (IOException e) {
                log.error("Error al eliminar archivo PDF: {}", e.getMessage());
            }
            proofFileRepository.delete(pf);
        });

        proofRepository.delete(proof);
    }

    @Transactional
    public ProofResponse createProof(ProofRequest request) {
        if (request.getSenderId() == null) {
            throw new AppException("senderId es requerido", HttpStatus.BAD_REQUEST);
        }
        if (request.getReceiverId() == null) {
            throw new AppException("receiverId es requerido", HttpStatus.BAD_REQUEST);
        }
        if (request.getActivityId() == null) {
            throw new AppException("activityId es requerido", HttpStatus.BAD_REQUEST);
        }
        if (request.getEventId() == null) {
            throw new AppException("eventId es requerido", HttpStatus.BAD_REQUEST);
        }
        if (request.getRole() == null) {
            throw new AppException("role es requerido", HttpStatus.BAD_REQUEST);
        }

        Sender sender = senderRepository.findById(request.getSenderId())
                .orElseThrow(() -> new AppException(SENDER_NOT_FOUND, HttpStatus.NOT_FOUND));
        Receiver receiver = receiverRepository.findById(request.getReceiverId())
                .orElseThrow(() -> new AppException("Receptor no encontrado", HttpStatus.NOT_FOUND));
        Activity activity = activityRepository.findById(request.getActivityId())
                .orElseThrow(() -> new AppException(ACTIVITY_NOT_FOUND, HttpStatus.NOT_FOUND));
        Event event = eventRepository.findById(request.getEventId())
                .orElseThrow(() -> new AppException(EVENT_NOT_FOUND, HttpStatus.NOT_FOUND));

        EParticipationRole role = request.getRole();
        int currentYear = LocalDate.now(ZoneId.systemDefault()).getYear();
        long count = proofRepository.countByRoleAndYear(role, currentYear);
        String folio = folioGenerator.generateFolio(role, count + 1, currentYear);

        Proof proof = Proof.builder()
                .folio(folio)
                .sender(sender)
                .receiver(receiver)
                .activity(activity)
                .event(event)
                .role(role)
                .date(LocalDate.now(ZoneId.systemDefault()))
                .build();

        proofRepository.save(proof);
        return proofMapper.toDto(proof);
    }

    @Transactional
    public ProofBulkResponse createProofsBulk(
            Long eventId,
            Long activityId,
            Long senderId,
            List<Receiver> receivers,
            List<EParticipationRole> roles
    ) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new AppException(EVENT_NOT_FOUND, HttpStatus.NOT_FOUND));
        Activity activity = activityRepository.findById(activityId)
                .orElseThrow(() -> new AppException(ACTIVITY_NOT_FOUND, HttpStatus.NOT_FOUND));
        Sender sender = senderRepository.findById(senderId)
                .orElseThrow(() -> new AppException(SENDER_NOT_FOUND, HttpStatus.NOT_FOUND));

        int currentYear = LocalDate.now(ZoneId.systemDefault()).getYear();

        Map<EParticipationRole, Long> roleCountBases = new EnumMap<>(EParticipationRole.class);
        Map<EParticipationRole, Long> roleIncrements = new EnumMap<>(EParticipationRole.class);

        for (EParticipationRole r : roles) {
            roleCountBases.putIfAbsent(r, proofRepository.countByRoleAndYear(r, currentYear));
            roleIncrements.putIfAbsent(r, 0L);
        }

        List<String> generatedFolios = new ArrayList<>();
        List<String> errors = new ArrayList<>();
        int successCount = 0;

        for (int i = 0; i < receivers.size(); i++) {
            try {
                EParticipationRole rowRole = roles.get(i);
                long base = roleCountBases.get(rowRole);
                long increment = roleIncrements.get(rowRole);
                String folio = folioGenerator.generateFolio(rowRole, base + increment + 1, currentYear);
                roleIncrements.put(rowRole, increment + 1);

                Proof proof = Proof.builder()
                        .folio(folio)
                        .sender(sender)
                        .receiver(receivers.get(i))
                        .activity(activity)
                        .event(event)
                        .role(rowRole)
                        .date(LocalDate.now(ZoneId.systemDefault()))
                        .build();

                proofRepository.save(proof);
                generatedFolios.add(folio);
                successCount++;
            } catch (Exception e) {
                errors.add("Fila " + (i + 1) + ": " + e.getMessage());
            }
        }

        return ProofBulkResponse.builder()
                .totalRows(receivers.size())
                .successCount(successCount)
                .errorCount(errors.size())
                .generatedFolios(generatedFolios)
                .errors(errors)
                .build();
    }

    public List<ProofResponse> getByActivity(Long activityId) {
        return proofMapper.toDtos(proofRepository.findByActivityActivityId(activityId));
    }

    @Transactional
    public ProofBulkResponse uploadAndCreateProofs(MultipartFile file, Long eventId, Long activityId, Long senderId, String role) {
        try {
            EParticipationRole defaultRole = role != null
                    ? EParticipationRole.valueOf(role.trim().toUpperCase())
                    : null;

            List<ExcelService.ParticipantRow> rows = excelService.parseParticipants(file, defaultRole);

            List<String> rowErrors = rows.stream()
                    .filter(r -> r.error() != null)
                    .map(r -> r.receiver().getName() + " " + r.receiver().getLastName() + ": " + r.error())
                    .toList();

            List<ExcelService.ParticipantRow> validRows = rows.stream()
                    .filter(r -> r.error() == null)
                    .toList();

            if (validRows.isEmpty()) {
                List<String> allErrors = new ArrayList<>(rowErrors);
                if (allErrors.isEmpty()) {
                    allErrors.add("No se encontraron participantes validos en el archivo. " +
                            "Asegurate de que las columnas NOMBRE y PRIMERAPELLIDO existan.");
                }
                return ProofBulkResponse.builder()
                        .totalRows(rows.size())
                        .successCount(0)
                        .errorCount(allErrors.size())
                        .errors(allErrors)
                        .build();
            }

            List<Receiver> receivers = validRows.stream()
                    .map(ExcelService.ParticipantRow::receiver)
                    .toList();

            List<EParticipationRole> roles = validRows.stream()
                    .map(ExcelService.ParticipantRow::role)
                    .toList();

            ProofBulkResponse response = createProofsBulk(
                    eventId, activityId, senderId, receivers, roles
            );

            if (!rowErrors.isEmpty()) {
                response.getErrors().addAll(0, rowErrors);
                response.setErrorCount(response.getErrorCount() + rowErrors.size());
                response.setTotalRows(response.getTotalRows() + rowErrors.size());
            }

            return response;
        } catch (IOException e) {
            return ProofBulkResponse.builder()
                    .totalRows(0)
                    .successCount(0)
                    .errorCount(1)
                    .errors(List.of("Error al leer el archivo: " + e.getMessage()))
                    .build();
        } catch (IllegalArgumentException e) {
            return ProofBulkResponse.builder()
                    .totalRows(0)
                    .successCount(0)
                    .errorCount(1)
                    .errors(List.of("Rol invalido. Usa: PONENTE, PARTICIPANTE, ORGANIZADOR o RECONOCIMIENTO"))
                    .build();
        }
    }

    public byte[] generatePdf(String folio) {
        Path outputDir = Paths.get(generatedDir).toAbsolutePath().normalize();
        Path filePath = outputDir.resolve("constancia-" + folio + ".pdf");

        if (Files.exists(filePath)) {
            try {
                return Files.readAllBytes(filePath);
            } catch (IOException e) {
                throw new AppException("Error al leer el archivo PDF: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        Proof proof = proofRepository.findById(folio)
                .orElseThrow(() -> new AppException(PROOF_NOT_FOUND + folio, HttpStatus.NOT_FOUND));
        return pdfGenerationService.generateCertificate(proof);
    }

    @Transactional
    public CanvasPdfResponse generatePdfs(CanvasPdfRequest request) {
        try {
            String base64Data = request.getCanvasImage();
            if (base64Data.contains(",")) {
                base64Data = base64Data.split(",")[1];
            }
            byte[] imageBytes = Base64.getDecoder().decode(base64Data);

            BufferedImage bufferedImage = ImageIO.read(new ByteArrayInputStream(imageBytes));
            if (bufferedImage == null) {
                throw new AppException("No se pudo decodificar la imagen", HttpStatus.BAD_REQUEST);
            }
            int width = bufferedImage.getWidth();
            int height = bufferedImage.getHeight();

            Path outputDir = Paths.get(generatedDir).toAbsolutePath().normalize();
            Files.createDirectories(outputDir);

            List<List<String>> persons = request.getData();
            List<String> generatedFolios = new ArrayList<>();
            List<String> generatedPaths = new ArrayList<>();

            Sender sender = null;
            Activity activity = null;
            Event event = null;
            EParticipationRole role = null;
            long roleCountBase = 0;
            long roleIncrement = 0;
            int currentYear = LocalDate.now(ZoneId.systemDefault()).getYear();

            if (request.getSenderId() != null && request.getActivityId() != null
                    && request.getEventId() != null && request.getRole() != null) {
                sender = senderRepository.findById(request.getSenderId())
                        .orElseThrow(() -> new AppException(SENDER_NOT_FOUND, HttpStatus.NOT_FOUND));
                activity = activityRepository.findById(request.getActivityId())
                        .orElseThrow(() -> new AppException(ACTIVITY_NOT_FOUND, HttpStatus.NOT_FOUND));
                event = eventRepository.findById(request.getEventId())
                        .orElseThrow(() -> new AppException(EVENT_NOT_FOUND, HttpStatus.NOT_FOUND));
                role = EParticipationRole.valueOf(request.getRole().trim().toUpperCase());
                roleCountBase = proofRepository.countByRoleAndYear(role, currentYear);
            }

            int count = 0;
            for (int i = 0; i < persons.size(); i++) {
                List<String> person = persons.get(i);

                String nombre = !person.isEmpty() ? person.get(0) : "";
                String primerApellido = person.size() > 1 ? person.get(1) : "";
                String segundoApellido = person.size() > 2 ? person.get(2) : "";
                String gradoAcademico = person.size() > 3 ? person.get(3) : "";

                String nombreCompleto = String.join(" ",
                        List.of(nombre, primerApellido, segundoApellido).stream()
                                .filter(s -> s != null && !s.isBlank())
                                .toArray(String[]::new));

                String folio = null;
                if (sender != null && activity != null && event != null && role != null) {
                    roleIncrement++;
                    folio = folioGenerator.generateFolio(role, roleCountBase + roleIncrement, currentYear);
                }

                String filename = "constancia-" + (folio != null ? folio : nombreCompleto.replaceAll("\\s+", "_")) + ".pdf";
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

                if (folio != null) {
                    Receiver receiver = Receiver.builder()
                            .name(nombre)
                            .lastName(primerApellido)
                            .twoLastName(segundoApellido.isEmpty() ? null : segundoApellido)
                            .academicGrade(gradoAcademico.isEmpty() ? null : gradoAcademico)
                            .build();
                    receiver = receiverRepository.save(receiver);

                    Proof proof = Proof.builder()
                            .folio(folio)
                            .sender(sender)
                            .receiver(receiver)
                            .activity(activity)
                            .event(event)
                            .role(role)
                            .date(LocalDate.now(ZoneId.systemDefault()))
                            .build();
                    proofRepository.save(proof);

                    ProofFile proofFile = ProofFile.builder()
                            .folio(folio)
                            .rutaPdf(filePath.toString())
                            .fechaCreacion(LocalDateTime.now(ZoneId.systemDefault()))
                            .build();
                    proofFileRepository.save(proofFile);

                    generatedFolios.add(folio);
                    generatedPaths.add(filePath.toString());
                }

                count++;
            }

            return CanvasPdfResponse.builder()
                    .count(count)
                    .path(outputDir.toString())
                    .generatedFolios(generatedFolios.isEmpty() ? null : generatedFolios)
                    .generatedPaths(generatedPaths.isEmpty() ? null : generatedPaths)
                    .build();
        } catch (AppException e) {
            throw e;
        } catch (Exception e) {
            throw new AppException("Error al generar constancias: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
