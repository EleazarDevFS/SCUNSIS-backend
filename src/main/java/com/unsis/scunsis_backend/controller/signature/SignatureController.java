package com.unsis.scunsis_backend.controller.signature;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.unsis.scunsis_backend.dto.request.signature.SignatureRequest;
import com.unsis.scunsis_backend.dto.response.signature.SignatureResponse;
import com.unsis.scunsis_backend.service.signature.SignatureService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/scunsis/api/v1/signature")
@RequiredArgsConstructor
public class SignatureController {

    private final SignatureService signatureService;

    @GetMapping("/{signatureId}")
    public ResponseEntity<SignatureResponse> getById(@PathVariable long signatureId){
        return ResponseEntity.ok(signatureService.getById(signatureId));
    }

    @GetMapping
    public ResponseEntity<List<SignatureResponse>> getAll(){
        return ResponseEntity.ok(signatureService.getAll());
    }

    @PostMapping
    public ResponseEntity<Void> createSignature(@RequestBody SignatureRequest request){
        signatureService.createSignature(request);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{signatureId}")
    public ResponseEntity<Void> deleteById(@PathVariable long signatureId){
        signatureService.deleteById(signatureId);
        return ResponseEntity.noContent().build();
    }
}
