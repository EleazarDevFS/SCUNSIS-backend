package com.unsis.scunsis_backend.controller.sender;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.unsis.scunsis_backend.dto.request.sender.SenderRequest;
import com.unsis.scunsis_backend.dto.response.sender.SenderResponse;
import com.unsis.scunsis_backend.service.sender.SenderService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/scunsis/api/v1/sender")
public class SenderController {

    private final SenderService senderService;

    @GetMapping
    public ResponseEntity<List<SenderResponse>> getAll(){
        return ResponseEntity.ok(senderService.getAll());
    }

    @GetMapping("/{senderId}")
    public ResponseEntity<SenderResponse> getById(@PathVariable long senderId){
        return ResponseEntity.ok(senderService.getById(senderId));
    }

    @PostMapping
    public ResponseEntity<Void> createSender(@RequestBody SenderRequest request){
        senderService.createSender(request);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteById(@PathVariable long senderId){
        senderService.deleteById(senderId);
        return ResponseEntity.noContent().build();
    }

}
