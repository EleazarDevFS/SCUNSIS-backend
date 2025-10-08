package com.unsis.scunsis_backend.mapper.sender;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.unsis.scunsis_backend.dto.request.sender.SenderRequest;
import com.unsis.scunsis_backend.dto.response.sender.SenderResponse;
import com.unsis.scunsis_backend.mapper.BaseMapper;
import com.unsis.scunsis_backend.model.sender.Sender;

import lombok.Data;

@Data
@Component
public class SenderMapper implements BaseMapper<SenderResponse, SenderRequest, Sender>{@Override
    
    public Sender toEntity(SenderRequest dto) {
        return Sender.builder()
        .campus(dto.getCampus())
        .name(dto.getName())
        .build();
    }

    public Sender toEntity(SenderResponse dto) {
        return Sender.builder()
        .campus(dto.getCampus())
        .name(dto.getName())
        .build();
    }


    @Override
    public SenderResponse toDto(Sender entity) {
        return SenderResponse.builder()
        .campus(entity.getCampus())
        .name(entity.getName())
        .build();
    }

    @Override
    public List<SenderResponse> toDtos(List<Sender> entities) {
       return entities.stream().map(this::toDto)
       .collect(Collectors.toList());
    }

    @Override
    public void updateEntity(SenderRequest request, Sender entity) {
        throw new UnsupportedOperationException("Unimplemented method 'updateEntity'");
    }


}
