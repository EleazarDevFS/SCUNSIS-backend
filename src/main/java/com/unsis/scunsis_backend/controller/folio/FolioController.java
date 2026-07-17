package com.unsis.scunsis_backend.controller.folio;

import com.unsis.scunsis_backend.model.enums.EParticipationRole;
import com.unsis.scunsis_backend.repository.proof.IProofRepository;
import com.unsis.scunsis_backend.util.FolioGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Year;
import java.util.Map;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class FolioController {

    private final FolioGenerator folioGenerator;
    private final IProofRepository proofRepository;

    @GetMapping("/folio")
    public ResponseEntity<Map<String, Object>> getFolio() {
        int year = Year.now().getValue();
        long count = proofRepository.countByRoleAndYear(EParticipationRole.PARTICIPANTE, year);
        String nextFolio = folioGenerator.generateFolio(EParticipationRole.PARTICIPANTE, count + 1, year);
        return ResponseEntity.ok(Map.of(
                "folio", nextFolio,
                "year", year,
                "next", count + 1
        ));
    }
}
