package com.unsis.scunsis_backend.mapper.event;

import java.util.List;

import com.unsis.scunsis_backend.dto.request.event.EventRequest;
import com.unsis.scunsis_backend.dto.response.event.EventResponse;
import com.unsis.scunsis_backend.mapper.BaseMapper;
import com.unsis.scunsis_backend.model.event.Event;

public class EventMapper implements BaseMapper<EventResponse, EventRequest, Event>{

    @Override
    public Event toEntity(EventRequest dto) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'toEntity'");
    }

    @Override
    public EventResponse toDto(Event entity) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'toDto'");
    }

    @Override
    public List<EventResponse> toDtos(List<Event> entities) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'toDtos'");
    }

    @Override
    public void updateEntity(EventRequest request, Event entity) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'updateEntity'");
    }



}
