package com.unsis.scunsis_backend.service.proof;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import com.unsis.scunsis_backend.dto.request.proof.ProofRequest;
import com.unsis.scunsis_backend.dto.response.proof.ProofResponse;
import com.unsis.scunsis_backend.mapper.proof.ProofMapper;
import com.unsis.scunsis_backend.model.enums.EProofType;
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
    
    public ProofResponse getById(@NonNull String folio){
        // Validar si existe 
        Optional<Proof> proof = proofRepository.findById(folio);
        ProofResponse proofResponse = proofMapper.toDto(proof.get());
        return proofResponse;
    }

    public void deleteById(@NonNull String folio){
        if(proofRepository.existsById(folio)){
            proofRepository.deleteById(folio);
        }else{
            throw new RuntimeException("This proof don't exist");
        }
    }

    public void createProof(@NonNull ProofRequest request){
        String folio = this.createNewFolio(request.getProofType());
        if(proofRepository.existsById(folio)){
            throw new RuntimeException("This proof exist in DB");
        }
        Proof proof = proofMapper.toEntity(request);
        proofRepository.save(proof);
    }
    @NonNull
    public String createNewFolio(EProofType type){
        String folio = type.getDescription()
        .concat(String.valueOf(LocalDate.now().getYear()))
        .concat(String.valueOf(proofRepository.countByType(type)))+"";
        return folio;
    }
}
