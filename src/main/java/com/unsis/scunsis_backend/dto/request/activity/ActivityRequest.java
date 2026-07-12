package com.unsis.scunsis_backend.dto.request.activity;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ActivityRequest {
    private Long eventId;
    private String activityName;
    private String activityDescription;
    private LocalDate startDate;
    private LocalDate endDate;
    private String activityPlace;
}
