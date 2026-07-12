package com.unsis.scunsis_backend.service.activity;

import com.unsis.scunsis_backend.dto.request.activity.ActivityRequest;
import com.unsis.scunsis_backend.dto.response.activity.ActivityResponse;
import com.unsis.scunsis_backend.exception.AppException;
import com.unsis.scunsis_backend.mapper.activity.ActivityMapper;
import com.unsis.scunsis_backend.model.activity.Activity;
import com.unsis.scunsis_backend.model.event.Event;
import com.unsis.scunsis_backend.repository.activity.IActivityRepository;
import com.unsis.scunsis_backend.repository.event.IEventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ActivityService {

    private final IActivityRepository activityRepository;
    private final ActivityMapper activityMapper;
    private final IEventRepository eventRepository;

    public ActivityResponse getById(long activityId) {
        Activity activity = activityRepository.findById(activityId)
                .orElseThrow(() -> new AppException("Actividad no encontrada con id: " + activityId, HttpStatus.NOT_FOUND));
        return activityMapper.toDto(activity);
    }

    public List<ActivityResponse> getAll() {
        return activityMapper.toDtos(activityRepository.findAll());
    }

    @Transactional
    public void createActivity(ActivityRequest request) {
        Event event = eventRepository.findById(request.getEventId())
                .orElseThrow(() -> new AppException("Evento no encontrado con id: " + request.getEventId(), HttpStatus.NOT_FOUND));

        Activity activity = activityMapper.toEntity(request);
        activity.setEvent(event);
        activityRepository.save(activity);
    }

    @Transactional
    public void deleteById(long activityId) {
        if (!activityRepository.existsById(activityId)) {
            throw new AppException("Actividad no encontrada con id: " + activityId, HttpStatus.NOT_FOUND);
        }
        activityRepository.deleteById(activityId);
    }
}
