package com.unsis.scunsis_backend.dto.response.event;

import java.time.LocalDate;

import com.unsis.scunsis_backend.model.enums.EEventType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EventResponse {
    private Long eventId;
    private EEventType eventType;
    private String eventName;
    private LocalDate startDate;
    private LocalDate endDate;
    private String eventPlace;
    private String eventDescription;
}
