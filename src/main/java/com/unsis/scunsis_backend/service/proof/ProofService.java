package com.unsis.scunsis_backend.service.proof;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import com.unsis.scunsis_backend.constants.Constant;
import com.unsis.scunsis_backend.exception.AppException;
import org.springframework.http.HttpStatus;
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
        if(!proofRepository.existsById(folio)){
            throw new AppException(Constant.NOT_FOUND_BY_ID, HttpStatus.NOT_FOUND);
        }
        Optional<Proof> proof = proofRepository.findById(folio);
        ProofResponse proofResponse = proofMapper.toDto(proof.get());
        return proofResponse;
    }

    public void deleteById(@NonNull String folio){
        if(proofRepository.existsById(folio)){
            proofRepository.deleteById(folio);
        }else{
            throw new AppException(Constant.NOT_FOUND_BY_ID, HttpStatus.NOT_FOUND);
        }
    }

    public void createProof(@NonNull ProofRequest request){
        String folio = this.createNewFolio(request.getProofType());
        if(proofRepository.existsById(folio)){
            throw new RuntimeException("This proof already exist in the DB");
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
