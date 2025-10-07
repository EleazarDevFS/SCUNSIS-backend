package com.unsis.scunsis_backend.mapper.event;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.unsis.scunsis_backend.dto.request.event.EventRequest;
import com.unsis.scunsis_backend.dto.response.event.EventResponse;
import com.unsis.scunsis_backend.mapper.BaseMapper;
import com.unsis.scunsis_backend.model.event.Event; 
@Component
public class EventMapper implements BaseMapper<EventResponse, EventRequest, Event>{

    @Override
    public Event toEntity(EventRequest dto) {
        return Event.builder()
        .endDate(dto.getEndDate())
        .eventDescription(dto.getEventDescription())
        .eventName(dto.getEventName())
        .eventPlace(dto.getEventPlace())
        .eventType(dto.getEventType())
        .endDate(dto.getEndDate())
        .startDate(dto.getStartDate())
        .build();
    }

    public Event toEntity(EventResponse dto) {
        return Event.builder()
        .endDate(dto.getEndDate())
        .eventDescription(dto.getEventDescription())
        .eventName(dto.getEventName())
        .eventPlace(dto.getEventPlace())
        .eventType(dto.getEventType())
        .startDate(dto.getStartDate())
        .endDate(dto.getEndDate())
        .build();
    }

    @Override
    public EventResponse toDto(Event entity) {
        return EventResponse.builder()
        .endDate(entity.getEndDate())
        .eventDescription(entity.getEventDescription())
        .eventName(entity.getEventName())
        .eventPlace(entity.getEventPlace())
        .eventType(entity.getEventType())
        .startDate(entity.getStartDate())
        .endDate(entity.getEndDate())
        .build();
    }

    @Override
    public List<EventResponse> toDtos(List<Event> entities) {
            return entities.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public void updateEntity(EventRequest request, Event entity) {
        throw new UnsupportedOperationException("Unimplemented method 'updateEntity'");
    }



}
