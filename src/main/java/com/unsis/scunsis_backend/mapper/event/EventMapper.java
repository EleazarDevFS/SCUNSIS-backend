package com.unsis.scunsis_backend.mapper.event;

import com.unsis.scunsis_backend.dto.request.event.EventRequest;
import com.unsis.scunsis_backend.dto.response.event.EventResponse;
import com.unsis.scunsis_backend.mapper.BaseMapper;
import com.unsis.scunsis_backend.model.event.Event;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class EventMapper implements BaseMapper<EventResponse, EventRequest, Event> {

    @Override
    public Event toEntity(EventRequest request) {
        return Event.builder()
                .eventType(request.getEventType())
                .eventName(request.getEventName())
                .startDate(request.getStartDate())
                .endDate(request.getEndDate())
                .eventPlace(request.getEventPlace())
                .eventDescription(request.getEventDescription())
                .build();
    }

    @Override
    public EventResponse toDto(Event entity) {
        return EventResponse.builder()
                .eventId(entity.getEventId())
                .eventType(entity.getEventType())
                .eventName(entity.getEventName())
                .startDate(entity.getStartDate())
                .endDate(entity.getEndDate())
                .eventPlace(entity.getEventPlace())
                .eventDescription(entity.getEventDescription())
                .build();
    }

    @Override
    public List<EventResponse> toDtos(List<Event> entities) {
        return entities.stream().map(this::toDto).collect(Collectors.toList());
    }

    @Override
    public void updateEntity(EventRequest request, Event entity) {
        if (request.getEventType() != null) {
            entity.setEventType(request.getEventType());
        }
        if (request.getEventName() != null) {
            entity.setEventName(request.getEventName());
        }
        if (request.getStartDate() != null) {
            entity.setStartDate(request.getStartDate());
        }
        if (request.getEndDate() != null) {
            entity.setEndDate(request.getEndDate());
        }
        if (request.getEventPlace() != null) {
            entity.setEventPlace(request.getEventPlace());
        }
        if (request.getEventDescription() != null) {
            entity.setEventDescription(request.getEventDescription());
        }
    }
}
