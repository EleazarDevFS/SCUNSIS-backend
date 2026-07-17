package com.unsis.scunsis_backend.controller.receiver;

import com.unsis.scunsis_backend.dto.request.receiver.ReceiverRequest;
import com.unsis.scunsis_backend.dto.response.receiver.ReceiverResponse;
import com.unsis.scunsis_backend.service.receiver.ReceiverService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/receiver")
@RequiredArgsConstructor
public class ReceiverController {

    private final ReceiverService receiverService;

    @PreAuthorize("hasAnyRole('ADMIN', 'CAPTURISTA')")
    @GetMapping("/{receiverId}")
    public ResponseEntity<ReceiverResponse> getById(@PathVariable long receiverId) {
        return ResponseEntity.ok(receiverService.getById(receiverId));
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'CAPTURISTA')")
    @GetMapping
    public ResponseEntity<List<ReceiverResponse>> getAll() {
        return ResponseEntity.ok(receiverService.getAll());
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<ReceiverResponse> createReceiver(@RequestBody ReceiverRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(receiverService.createReceiver(request));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{receiverId}")
    public ResponseEntity<ReceiverResponse> updateReceiver(@PathVariable long receiverId, @RequestBody ReceiverRequest request) {
        return ResponseEntity.ok(receiverService.updateReceiver(receiverId, request));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{receiverId}")
    public ResponseEntity<Void> deleteById(@PathVariable long receiverId) {
        receiverService.deleteById(receiverId);
        return ResponseEntity.noContent().build();
    }
}
