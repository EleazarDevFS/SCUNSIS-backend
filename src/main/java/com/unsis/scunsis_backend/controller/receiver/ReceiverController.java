package com.unsis.scunsis_backend.controller.receiver;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.unsis.scunsis_backend.dto.request.receiver.ReceiverRequest;
import com.unsis.scunsis_backend.dto.response.receiver.ReceiverResponse;
import com.unsis.scunsis_backend.service.receiver.ReceiverService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/scunsis/api/v1/receiver")
public class ReceiverController {

    private final ReceiverService receiverService;

    @GetMapping("/{receiverId}")
    public ResponseEntity<ReceiverResponse> getById(@PathVariable long receiverId){
        return ResponseEntity.ok(receiverService.getById(receiverId));
    }

    @GetMapping
    public ResponseEntity<List<ReceiverResponse>> getAll(){
        return ResponseEntity.ok(receiverService.getAll());
    }

    @PostMapping
    public ResponseEntity<Void> createReceiver(@RequestBody ReceiverRequest request){
        receiverService.createReceiver(request);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{receiverId}")
    public ResponseEntity<Void> deleteById(@PathVariable long receiverId){
        receiverService.deleteById(receiverId);
        return ResponseEntity.noContent().build();
    }

}
