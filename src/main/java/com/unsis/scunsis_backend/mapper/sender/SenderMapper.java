package com.unsis.scunsis_backend.mapper.sender;

import com.unsis.scunsis_backend.dto.request.sender.SenderRequest;
import com.unsis.scunsis_backend.dto.response.sender.SenderResponse;
import com.unsis.scunsis_backend.mapper.BaseMapper;
import com.unsis.scunsis_backend.model.sender.Sender;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class SenderMapper implements BaseMapper<SenderResponse, SenderRequest, Sender> {

    @Override
    public Sender toEntity(SenderRequest request) {
        return Sender.builder()
                .name(request.getName())
                .campus(request.getCampus())
                .build();
    }

    @Override
    public SenderResponse toDto(Sender entity) {
        return SenderResponse.builder()
                .senderId(entity.getSenderId())
                .name(entity.getName())
                .campus(entity.getCampus())
                .build();
    }

    @Override
    public List<SenderResponse> toDtos(List<Sender> entities) {
        return entities.stream().map(this::toDto).toList();
    }

    @Override
    public void updateEntity(SenderRequest request, Sender entity) {
        if (request.getName() != null) {
            entity.setName(request.getName());
        }
        if (request.getCampus() != null) {
            entity.setCampus(request.getCampus());
        }
    }
}
