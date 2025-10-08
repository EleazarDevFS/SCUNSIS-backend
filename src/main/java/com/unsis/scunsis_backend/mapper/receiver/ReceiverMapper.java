package com.unsis.scunsis_backend.mapper.receiver;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.unsis.scunsis_backend.dto.request.receiver.ReceiverRequest;
import com.unsis.scunsis_backend.dto.response.receiver.ReceiverResponse;
import com.unsis.scunsis_backend.mapper.BaseMapper;
import com.unsis.scunsis_backend.model.receiver.Receiver;

import lombok.Data;

@Data
@Component
public class ReceiverMapper implements BaseMapper<ReceiverResponse, ReceiverRequest, Receiver>{@Override
    
    public Receiver toEntity(ReceiverRequest dto) {
        return Receiver.builder()
        .academicGrade(dto.getAcademicGrade())
        .email(dto.getEmail())
        .lastName(dto.getLastName())
        .name(dto.getName())
        .phone(dto.getPhone())
        .twoLastName(dto.getTwoLastName())
        .build();
    }

    public Receiver toEntity(ReceiverResponse dto) {
        return Receiver.builder()
        .academicGrade(dto.getAcademicGrade())
        .email(dto.getEmail())
        .lastName(dto.getLastName())
        .name(dto.getName())
        .phone(dto.getPhone())
        .twoLastName(dto.getTwoLastName())
        .build();
    }


    @Override
    public ReceiverResponse toDto(Receiver entity) {
        return ReceiverResponse.builder()
        .academicGrade(entity.getAcademicGrade())
        .email(entity.getEmail())
        .lastName(entity.getLastName())
        .name(entity.getName())
        .phone(entity.getPhone())
        .twoLastName(entity.getTwoLastName())
        .build();
    }

    @Override
    public List<ReceiverResponse> toDtos(List<Receiver> entities) {
        return entities.stream()
        .map(this::toDto).collect(Collectors.toList());
    }

    @Override
    public void updateEntity(ReceiverRequest request, Receiver entity) {
        throw new UnsupportedOperationException("Unimplemented method 'updateEntity'");
    }
    
}
