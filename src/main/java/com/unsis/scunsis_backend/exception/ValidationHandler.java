package com.unsis.scunsis_backend.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;

@Slf4j
@RestControllerAdvice
public class ValidationHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, Object> handleValidationErrors(
            MethodArgumentNotValidException ex,
            WebRequest request) {

        log.warn("Errores de validación: {}", ex.getMessage());

        Map<String, Object> response = new HashMap<>();
        Map<String, List<String>> fieldErrors = new HashMap<>();
        List<String> globalErrors = new ArrayList<>();

        ex.getBindingResult().getAllErrors().forEach(error -> {
            if (error instanceof FieldError fieldError) {
                fieldErrors.computeIfAbsent(fieldError.getField(), key -> new ArrayList<>())
                        .add(fieldError.getDefaultMessage());
            } else {
                globalErrors.add(error.getDefaultMessage());
            }
        });

        response.put("timestamp", LocalDateTime.now(ZoneId.systemDefault()));
        response.put("status", HttpStatus.BAD_REQUEST.value());
        response.put("error", "Bad Request");
        response.put("message", "Validation failed");
        response.put("path", request.getDescription(false).replace("uri=", ""));
        response.put("fieldErrors", fieldErrors);

        if (!globalErrors.isEmpty()) {
            response.put("globalErrors", globalErrors);
        }

        return response;
    }
}