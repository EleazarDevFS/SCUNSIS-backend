package com.unsis.scunsis_backend.mapper.proof;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.unsis.scunsis_backend.dto.request.proof.ProofRequest;
import com.unsis.scunsis_backend.dto.response.proof.ProofResponse;
import com.unsis.scunsis_backend.mapper.BaseMapper;
import com.unsis.scunsis_backend.model.proof.Proof;

import io.micrometer.common.lang.NonNull;
import lombok.Data;

@Data
@Component
public class ProofMapper implements BaseMapper<ProofResponse, ProofRequest, Proof>{@Override
    public Proof toEntity(@NonNull ProofRequest dto) {
        return Proof.builder()
        .activity(dto.getActivity())
        .date(dto.getDate())
        .event(dto.getEvent())
        .proofType(dto.getProofType())
        .receiver(dto.getReceiver())
        .sender(dto.getSender())
        .signature(dto.getSignature())
        .build();
    }

    @Override
    public ProofResponse toDto(Proof entity) {
       return ProofResponse.builder()
       .activity(entity.getActivity())
       .date(entity.getDate())
       .event(entity.getEvent())
       .folio(entity.getFolio())
       .proofType(entity.getProofType())
       .receiver(entity.getReceiver())
       .sender(entity.getSender())
       .signature(entity.getSignature())
       .build();
    }

    @Override
    public List<ProofResponse> toDtos(List<Proof> entities) {
        return entities.stream()
        .map(this::toDto)
        .collect(Collectors.toList());
    }

    @Override
    public void updateEntity(ProofRequest request, Proof entity) {
        throw new UnsupportedOperationException("Unimplemented method 'updateEntity'");
    }


}
