package com.unsis.scunsis_backend.service.proof;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.unsis.scunsis_backend.dto.request.proof.ProofRequest;
import com.unsis.scunsis_backend.dto.response.proof.ProofResponse;
import com.unsis.scunsis_backend.mapper.proof.ProofMapper;
import com.unsis.scunsis_backend.model.proof.Proof;
import com.unsis.scunsis_backend.repository.proof.IProofRepository;

import lombok.Data;
import lombok.RequiredArgsConstructor;
@Data
@Service
@RequiredArgsConstructor
public class ProofService {

    private final IProofRepository proofRepository;
    private final ProofMapper proofMapper;

    public List<ProofResponse> getAll(){
        List<Proof> proofs = proofRepository.findAll();
        List<ProofResponse> proofsResponse = proofMapper.toDtos(proofs);
        return proofsResponse;
    }
    
    public ProofResponse getById(String folio){
        // Validar si existe 
        Optional<Proof> proof = proofRepository.findById(folio);
        ProofResponse proofResponse = proofMapper.toDto(proof.get());
        return proofResponse;
    }

    public void deleteById(String folio){
        // Validar si existe
        proofRepository.deleteById(folio);
    }

    public void createProof(ProofRequest request){
        // Validar si ya existe 
        proofRepository.save(proofMapper.toEntity(request));
    }
}
