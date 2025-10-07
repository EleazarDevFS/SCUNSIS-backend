package com.unsis.scunsis_backend.dto.response.activity;

import java.sql.Date;

import com.unsis.scunsis_backend.model.event.Event;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ActivityResponse {
    
    private long activityId;

    private Event eventId;

    private String activityName;

    private String activityDescription;

    private Date startDate;

    private Date endDate;

    private String activityPlace;
}
