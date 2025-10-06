package com.unsis.scunsis_backend.mapper.activity;

import java.util.List;
import java.util.stream.Collectors;

import com.unsis.scunsis_backend.dto.request.activity.ActivityRequest;
import com.unsis.scunsis_backend.dto.response.activity.ActivityResponse;
import com.unsis.scunsis_backend.mapper.BaseMapper;
import com.unsis.scunsis_backend.model.activity.Activity;

import lombok.Data;

@Data
public class ActivityMapper implements BaseMapper<ActivityResponse, ActivityRequest, Activity> {
    
    @Override
    public Activity toEntity(ActivityRequest dto) {
        return Activity.builder()
        .activityDescription(dto.getActivityDescription())
        .activityName(dto.getActivityName())
        .activityPlace(dto.getActivityPlace())
        .endDate(dto.getEndDate())
        .eventId(dto.getEventId())
        .startDate(dto.getStartDate())
        .build();
    }

    public Activity toEntity(ActivityResponse dto) {
        return Activity.builder()
        .activityDescription(dto.getActivityDescription())
        .activityName(dto.getActivityName())
        .activityPlace(dto.getActivityPlace())
        .endDate(dto.getEndDate())
        .eventId(dto.getEventId())
        .startDate(dto.getStartDate())
        .build();
    } 

    @Override
    public ActivityResponse toDto(Activity entity) {
       return ActivityResponse.builder()
       .activityDescription(entity.getActivityDescription())
       .activityName(entity.getActivityName())
       .activityPlace(entity.getActivityPlace())
       .endDate(entity.getEndDate())
       .eventId(entity.getEventId())
       .startDate(entity.getStartDate())
       .build();
    }

    @Override
    public List<ActivityResponse> toDtos(List<Activity> entities) {
       return entities.stream()
       .map(this::toDto)
       .collect(Collectors.toList());
    }

    @Override
    public void updateEntity(ActivityRequest request, Activity entity) {
        throw new UnsupportedOperationException("Unimplemented method 'updateEntity'");
    }

}
