package com.unsis.scunsis_backend.service.event;

import java.util.List;
import java.util.Optional;

import com.unsis.scunsis_backend.constants.Constant;
import com.unsis.scunsis_backend.exception.AppException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.unsis.scunsis_backend.dto.request.event.EventRequest;
import com.unsis.scunsis_backend.dto.response.event.EventResponse;
import com.unsis.scunsis_backend.mapper.event.EventMapper;
import com.unsis.scunsis_backend.model.event.Event;
import com.unsis.scunsis_backend.repository.event.IEventRepository;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@Service
@RequiredArgsConstructor
public class EventService {

    private final EventMapper eventMapper;
    private final IEventRepository eventRepository;

    public List<EventResponse> getAllEvent(){
        List<EventResponse> events = eventMapper.toDtos(eventRepository.findAll());
        return events;
    }

    public void createEvent(EventRequest request){
        eventRepository.save(eventMapper.toEntity(request));
    }

    public EventResponse getEventById(Long eventId){
        if(!eventRepository.existsById(eventId)){
            throw new AppException(Constant.NOT_FOUND_BY_ID, HttpStatus.NOT_FOUND);
        }
        Optional<Event> event = eventRepository.findById(eventId);
            EventResponse eventResponse = eventMapper.toDto(event.get());
            return eventResponse;
    }

    public void deleteEventById(long eventId){
        if(!eventRepository.existsById(eventId)){
            throw new AppException(Constant.NOT_FOUND_BY_ID, HttpStatus.NOT_FOUND);
        }
        eventRepository.deleteById(eventId);
    }
}
