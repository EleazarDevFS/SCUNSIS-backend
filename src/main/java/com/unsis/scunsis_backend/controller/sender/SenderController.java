package com.unsis.scunsis_backend.controller.sender;

import com.unsis.scunsis_backend.dto.request.sender.SenderRequest;
import com.unsis.scunsis_backend.dto.response.sender.SenderResponse;
import com.unsis.scunsis_backend.service.sender.SenderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/scunsis/api/v1/sender")
@RequiredArgsConstructor
public class SenderController {

    private final SenderService senderService;

    @GetMapping
    public ResponseEntity<List<SenderResponse>> getAll() {
        return ResponseEntity.ok(senderService.getAll());
    }

    @GetMapping("/{senderId}")
    public ResponseEntity<SenderResponse> getById(@PathVariable long senderId) {
        return ResponseEntity.ok(senderService.getById(senderId));
    }

    @PostMapping
    public ResponseEntity<Void> createSender(@RequestBody SenderRequest request) {
        senderService.createSender(request);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping("/{senderId}")
    public ResponseEntity<Void> deleteById(@PathVariable long senderId) {
        senderService.deleteById(senderId);
        return ResponseEntity.noContent().build();
    }
}
