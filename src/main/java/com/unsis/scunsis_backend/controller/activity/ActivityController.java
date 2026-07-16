package com.unsis.scunsis_backend.controller.activity;

import com.unsis.scunsis_backend.dto.request.activity.ActivityRequest;
import com.unsis.scunsis_backend.dto.response.activity.ActivityResponse;
import com.unsis.scunsis_backend.service.activity.ActivityService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/activity")
@RequiredArgsConstructor
public class ActivityController {

    private final ActivityService activityService;

    @GetMapping("/{activityId}")
    public ResponseEntity<ActivityResponse> getById(@PathVariable long activityId) {
        return ResponseEntity.ok(activityService.getById(activityId));
    }

    @GetMapping
    public ResponseEntity<List<ActivityResponse>> getAllActivities() {
        return ResponseEntity.ok(activityService.getAll());
    }

    @PostMapping
    public ResponseEntity<Void> createActivity(@RequestBody ActivityRequest request) {
        activityService.createActivity(request);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping("/{activityId}")
    public ResponseEntity<Void> deleteById(@PathVariable long activityId) {
        activityService.deleteById(activityId);
        return ResponseEntity.noContent().build();
    }
}
