package com.unsis.scunsis_backend.controller.activity;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.unsis.scunsis_backend.dto.request.activity.ActivityRequest;
import com.unsis.scunsis_backend.dto.response.activity.ActivityResponse;
import com.unsis.scunsis_backend.service.activity.ActivityService;

import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;



@RestController
@RequestMapping("/scunsis/api/v1/activity")
@RequiredArgsConstructor
public class ActivityController {

    private final ActivityService activityService;

    @GetMapping("/{activityId}")
    public ResponseEntity<ActivityResponse> getById(@PathVariable long activityId){
        return ResponseEntity.ok(activityService.getById(activityId));
    }

    @GetMapping
    public ResponseEntity<List<ActivityResponse>> getAllActivities(){
        return ResponseEntity.ok(activityService.getAll());
    }

    @PostMapping
    public void postMethodName(@RequestBody ActivityRequest request) {
        activityService.createActivity(request);
    }
    
    @DeleteMapping
    public ResponseEntity<Void> deleteById(@PathVariable long activityId){
        activityService.deleteById(activityId);
        return ResponseEntity.noContent().build();
    }
}
