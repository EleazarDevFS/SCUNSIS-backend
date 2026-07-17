package com.unsis.scunsis_backend.controller.proof;

import com.unsis.scunsis_backend.dto.request.proof.CanvasPdfRequest;
import com.unsis.scunsis_backend.dto.request.proof.ProofRequest;
import com.unsis.scunsis_backend.dto.response.proof.CanvasPdfResponse;
import com.unsis.scunsis_backend.dto.response.proof.ProofBulkResponse;
import com.unsis.scunsis_backend.dto.response.proof.ProofResponse;
import com.unsis.scunsis_backend.service.proof.ProofService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/v1/proof")
@RequiredArgsConstructor
public class ProofController {

    private final ProofService proofService;

    @PreAuthorize("hasAnyRole('ADMIN', 'CAPTURISTA')")
    @PostMapping("/generate-pdfs")
    public ResponseEntity<CanvasPdfResponse> generatePdfs(@RequestBody CanvasPdfRequest request) {
        return ResponseEntity.ok(proofService.generatePdfs(request));
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
        return ResponseEntity.ok(proofService.uploadAndCreateProofs(file, eventId, activityId, senderId, role));
    }
}
