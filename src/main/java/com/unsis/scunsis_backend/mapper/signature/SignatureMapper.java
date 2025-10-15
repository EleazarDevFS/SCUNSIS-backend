package com.unsis.scunsis_backend.mapper.signature;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.unsis.scunsis_backend.dto.request.signature.SignatureRequest;
import com.unsis.scunsis_backend.dto.response.signature.SignatureResponse;
import com.unsis.scunsis_backend.mapper.BaseMapper;
import com.unsis.scunsis_backend.model.signature.Signature;

import lombok.Data;

@Data
@Component
public class SignatureMapper implements BaseMapper<SignatureResponse, SignatureRequest, Signature>{
    
    @Override
    public Signature toEntity(SignatureRequest dto) {
        return Signature.builder()
        .academicGrade(dto.getAcademicGrade())
        .lastName(dto.getLastName())
        .name(dto.getName())
        .position(dto.getPosition())
        .twoLastName(dto.getTwoLastName())
        .build();
    }

    @Override
    public SignatureResponse toDto(Signature entity) {
        return SignatureResponse.builder()
        .academicGrade(entity.getAcademicGrade())
        .lastName(entity.getLastName())
        .name(entity.getName())
        .position(entity.getPosition())
        .twoLastName(entity.getTwoLastName())
        .build();
    }

    @Override
    public List<SignatureResponse> toDtos(List<Signature> entities) {
        return entities.stream()
        .map(this::toDto).collect(Collectors.toList());
    }

    @Override
    public void updateEntity(SignatureRequest request, Signature entity) {
        throw new UnsupportedOperationException("Unimplemented method 'updateEntity'");
    }


}
