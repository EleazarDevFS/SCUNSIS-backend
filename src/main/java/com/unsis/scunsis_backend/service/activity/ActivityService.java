package com.unsis.scunsis_backend.service.activity;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.unsis.scunsis_backend.dto.request.activity.ActivityRequest;
import com.unsis.scunsis_backend.dto.response.activity.ActivityResponse;
import com.unsis.scunsis_backend.mapper.activity.ActivityMapper;
import com.unsis.scunsis_backend.model.activity.Activity;
import com.unsis.scunsis_backend.repository.activity.IActivityRepository;

import lombok.Data;
import lombok.RequiredArgsConstructor;
@Data
@Service
@RequiredArgsConstructor
public class ActivityService {

    private final IActivityRepository activityRepository;
    private final ActivityMapper activityMapper;

    public ActivityResponse getById(long activityId){
        Optional<Activity> activity = activityRepository.findById(activityId);
        ActivityResponse activityResponse = activityMapper.toDto(activity.get());
        return activityResponse;
    }

    public List<ActivityResponse> getAll(){
        List<ActivityResponse> activityResponse = activityMapper.toDtos(activityRepository.findAll());
        return activityResponse;
    }

    public void createActivity(ActivityRequest request){
        activityRepository.save(activityMapper.toEntity(request));
    }

    public void deleteById(long activityId){
        // Necesitamos primero confirmar o buscar que exista esta actividad antes de borrar
        activityRepository.deleteById(activityId);
    }

}
