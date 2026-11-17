package com.aditya.platform.order.controller;

import com.aditya.platform.order.service.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import java.time.Instant;
import java.util.Map;

@RestControllerAdvice
public class ApiExceptionHandler {
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<?> notFound(RuntimeException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(Map.of("timestamp", Instant.now(), "status", 404, "error", ex.getMessage()));
    }

    @ExceptionHandler({IllegalArgumentException.class, ProductNotAvailableException.class})
    public ResponseEntity<?> badRequest(RuntimeException ex) {
        return ResponseEntity.badRequest()
                .body(Map.of("timestamp", Instant.now(), "status", 400, "error", ex.getMessage()));
    }
}
