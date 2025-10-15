package com.unsis.scunsis_backend.controller.proof;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.unsis.scunsis_backend.dto.request.proof.ProofRequest;
import com.unsis.scunsis_backend.dto.response.proof.ProofResponse;
import com.unsis.scunsis_backend.service.proof.ProofService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/scunsis/api/v1/proof")
@RequiredArgsConstructor
public class ProofController {

    private final ProofService proofService;

    @GetMapping
    public ResponseEntity<List<ProofResponse>> getAll(){
        return ResponseEntity.ok(proofService.getAll());
    }

    @GetMapping("/{folio}")
    public ResponseEntity<ProofResponse> getById(@PathVariable String folio){
        return ResponseEntity.ok(proofService.getById(folio));
    }

    @PostMapping
    public ResponseEntity<Void> createProof(@RequestBody ProofRequest request){
        proofService.createProof(request);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{folio}")
    public ResponseEntity<Void> deleteProof(@PathVariable String folio){
        proofService.deleteById(folio);
        return ResponseEntity.noContent().build();
    }
}
