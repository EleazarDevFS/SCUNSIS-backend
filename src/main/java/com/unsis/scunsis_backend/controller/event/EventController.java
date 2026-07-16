package com.unsis.scunsis_backend.controller.event;

import com.unsis.scunsis_backend.dto.request.event.EventRequest;
import com.unsis.scunsis_backend.dto.response.event.EventResponse;
import com.unsis.scunsis_backend.service.event.EventService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/event")
@RequiredArgsConstructor
public class EventController {

    private final EventService eventService;

    @PostMapping
    public ResponseEntity<Void> createEvent(@RequestBody EventRequest request) {
        eventService.createEvent(request);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping("/{eventId}")
    public ResponseEntity<Void> deleteEventById(@PathVariable Long eventId) {
        eventService.deleteEventById(eventId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{eventId}")
    public ResponseEntity<EventResponse> getEventById(@PathVariable Long eventId) {
        return ResponseEntity.ok(eventService.getEventById(eventId));
    }

    @GetMapping
    public ResponseEntity<List<EventResponse>> getAllEvent() {
        return ResponseEntity.ok(eventService.getAllEvent());
    }
}
