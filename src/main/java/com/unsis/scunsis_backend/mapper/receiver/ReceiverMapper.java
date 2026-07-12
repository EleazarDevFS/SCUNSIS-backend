package com.unsis.scunsis_backend.mapper.receiver;

import com.unsis.scunsis_backend.dto.request.receiver.ReceiverRequest;
import com.unsis.scunsis_backend.dto.response.receiver.ReceiverResponse;
import com.unsis.scunsis_backend.mapper.BaseMapper;
import com.unsis.scunsis_backend.model.receiver.Receiver;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ReceiverMapper implements BaseMapper<ReceiverResponse, ReceiverRequest, Receiver> {

    @Override
    public Receiver toEntity(ReceiverRequest request) {
        return Receiver.builder()
                .name(request.getName())
                .lastName(request.getLastName())
                .twoLastName(request.getTwoLastName())
                .phone(request.getPhone())
                .email(request.getEmail())
                .academicGrade(request.getAcademicGrade())
                .build();
    }

    @Override
    public ReceiverResponse toDto(Receiver entity) {
        return ReceiverResponse.builder()
                .receiverId(entity.getReceiverId())
                .name(entity.getName())
                .lastName(entity.getLastName())
                .twoLastName(entity.getTwoLastName())
                .phone(entity.getPhone())
                .email(entity.getEmail())
                .academicGrade(entity.getAcademicGrade())
                .build();
    }

    @Override
    public List<ReceiverResponse> toDtos(List<Receiver> entities) {
        return entities.stream().map(this::toDto).collect(Collectors.toList());
    }

    @Override
    public void updateEntity(ReceiverRequest request, Receiver entity) {
        if (request.getName() != null) {
            entity.setName(request.getName());
        }
        if (request.getLastName() != null) {
            entity.setLastName(request.getLastName());
        }
        if (request.getTwoLastName() != null) {
            entity.setTwoLastName(request.getTwoLastName());
        }
        if (request.getPhone() != null) {
            entity.setPhone(request.getPhone());
        }
        if (request.getEmail() != null) {
            entity.setEmail(request.getEmail());
        }
        if (request.getAcademicGrade() != null) {
            entity.setAcademicGrade(request.getAcademicGrade());
        }
    }
}
