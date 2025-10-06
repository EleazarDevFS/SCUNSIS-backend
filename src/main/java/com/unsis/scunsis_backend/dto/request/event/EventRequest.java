package com.unsis.scunsis_backend.dto.request.event;

import java.sql.Date;

import com.unsis.scunsis_backend.model.enums.EEventType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EventRequest {

    private EEventType eventType;

    private String eventName;

    private Date startDate;

    private Date endDate;

    private String eventPlace;

    private String eventDescription;
}
