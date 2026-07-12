package com.unsis.scunsis_backend.dto.response.activity;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ActivityResponse {
    private Long activityId;
    private Long eventId;
    private String eventName;
    private String activityName;
    private String activityDescription;
    private LocalDate startDate;
    private LocalDate endDate;
    private String activityPlace;
}
