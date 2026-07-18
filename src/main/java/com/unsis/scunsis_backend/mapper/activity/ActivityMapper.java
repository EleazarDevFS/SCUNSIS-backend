package com.unsis.scunsis_backend.mapper.activity;

import com.unsis.scunsis_backend.dto.request.activity.ActivityRequest;
import com.unsis.scunsis_backend.dto.response.activity.ActivityResponse;
import com.unsis.scunsis_backend.mapper.BaseMapper;
import com.unsis.scunsis_backend.model.activity.Activity;
import com.unsis.scunsis_backend.model.event.Event;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ActivityMapper implements BaseMapper<ActivityResponse, ActivityRequest, Activity> {

    @Override
    public Activity toEntity(ActivityRequest request) {
        Activity activity = new Activity();
        activity.setEvent(Event.builder().eventId(request.getEventId()).build());
        activity.setActivityName(request.getActivityName());
        activity.setActivityDescription(request.getActivityDescription());
        activity.setStartDate(request.getStartDate());
        activity.setEndDate(request.getEndDate());
        activity.setActivityPlace(request.getActivityPlace());
        return activity;
    }

    @Override
    public ActivityResponse toDto(Activity entity) {
        return ActivityResponse.builder()
                .activityId(entity.getActivityId())
                .eventId(entity.getEvent() != null ? entity.getEvent().getEventId() : null)
                .eventName(entity.getEvent() != null ? entity.getEvent().getEventName() : null)
                .activityName(entity.getActivityName())
                .activityDescription(entity.getActivityDescription())
                .startDate(entity.getStartDate())
                .endDate(entity.getEndDate())
                .activityPlace(entity.getActivityPlace())
                .build();
    }

    @Override
    public List<ActivityResponse> toDtos(List<Activity> entities) {
        return entities.stream().map(this::toDto).toList();
    }

    @Override
    public void updateEntity(ActivityRequest request, Activity entity) {
        if (request.getEventId() != null) {
            entity.setEvent(Event.builder().eventId(request.getEventId()).build());
        }
        if (request.getActivityName() != null) {
            entity.setActivityName(request.getActivityName());
        }
        if (request.getActivityDescription() != null) {
            entity.setActivityDescription(request.getActivityDescription());
        }
        if (request.getStartDate() != null) {
            entity.setStartDate(request.getStartDate());
        }
        if (request.getEndDate() != null) {
            entity.setEndDate(request.getEndDate());
        }
        if (request.getActivityPlace() != null) {
            entity.setActivityPlace(request.getActivityPlace());
        }
    }
}
