package com.unsis.scunsis_backend.service.event;

import com.unsis.scunsis_backend.dto.request.event.EventRequest;
import com.unsis.scunsis_backend.dto.response.event.EventResponse;
import com.unsis.scunsis_backend.exception.AppException;
import com.unsis.scunsis_backend.mapper.event.EventMapper;
import com.unsis.scunsis_backend.repository.event.IEventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EventService {

    private final EventMapper eventMapper;
    private final IEventRepository eventRepository;

    public List<EventResponse> getAllEvent() {
        return eventMapper.toDtos(eventRepository.findAll());
    }

    @Transactional
    public void createEvent(EventRequest request) {
        eventRepository.save(eventMapper.toEntity(request));
    }

    public EventResponse getEventById(Long eventId) {
        return eventMapper.toDto(eventRepository.findById(eventId)
                .orElseThrow(() -> new AppException("Evento no encontrado con id: " + eventId, HttpStatus.NOT_FOUND)));
    }

    @Transactional
    public void deleteEventById(long eventId) {
        if (!eventRepository.existsById(eventId)) {
            throw new AppException("Evento no encontrado con id: " + eventId, HttpStatus.NOT_FOUND);
        }
        eventRepository.deleteById(eventId);
    }
}
