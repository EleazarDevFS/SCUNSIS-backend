package com.unsis.scunsis_backend.controller.activity;

import com.unsis.scunsis_backend.dto.request.activity.ActivityRequest;
import com.unsis.scunsis_backend.dto.response.activity.ActivityResponse;
import com.unsis.scunsis_backend.service.activity.ActivityService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/activity")
@RequiredArgsConstructor
public class ActivityController {

    private final ActivityService activityService;

    @PreAuthorize("hasAnyRole('ADMIN', 'CAPTURISTA')")
    @GetMapping("/{activityId}")
    public ResponseEntity<ActivityResponse> getById(@PathVariable long activityId) {
        return ResponseEntity.ok(activityService.getById(activityId));
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'CAPTURISTA')")
    @GetMapping
    public ResponseEntity<List<ActivityResponse>> getAllActivities() {
        return ResponseEntity.ok(activityService.getAll());
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<Void> createActivity(@RequestBody ActivityRequest request) {
        activityService.createActivity(request);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{activityId}")
    public ResponseEntity<ActivityResponse> updateActivity(@PathVariable long activityId, @RequestBody ActivityRequest request) {
        return ResponseEntity.ok(activityService.updateActivity(activityId, request));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{activityId}")
    public ResponseEntity<Void> deleteById(@PathVariable long activityId) {
        activityService.deleteById(activityId);
        return ResponseEntity.noContent().build();
    }
}
