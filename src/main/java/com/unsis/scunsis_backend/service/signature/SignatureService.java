package com.unsis.scunsis_backend.service.signature;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.unsis.scunsis_backend.dto.request.signature.SignatureRequest;
import com.unsis.scunsis_backend.dto.response.signature.SignatureResponse;
import com.unsis.scunsis_backend.mapper.signature.SignatureMapper;
import com.unsis.scunsis_backend.model.signature.Signature;
import com.unsis.scunsis_backend.repository.signature.ISignatureRepository;

import lombok.Data;
import lombok.RequiredArgsConstructor;
@Data
@Service
@RequiredArgsConstructor
public class SignatureService {

    private final ISignatureRepository signatureRepository;
    private final SignatureMapper signatureMapper;

    public SignatureResponse getById(long signatureId){
        Optional<Signature> signature = signatureRepository.findById(signatureId);
        SignatureResponse signatureResponse = signatureMapper.toDto(signature.get());
        return signatureResponse;
    }

    public List<SignatureResponse> getAll(){
        List<Signature> signatures = signatureRepository.findAll();
        List<SignatureResponse> signaturesResponses = signatureMapper.toDtos(signatures);
        return signaturesResponses;
    }

    public void createSignature(SignatureRequest request){
        // Validar 
        signatureRepository.save(signatureMapper.toEntity(request));
    }

    public void deleteById(long signatureId){
        // Validamos si existe
        signatureRepository.deleteById(signatureId);
    }

}
