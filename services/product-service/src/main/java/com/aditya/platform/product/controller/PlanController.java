package com.aditya.platform.product.controller;

import com.aditya.platform.product.dto.*;
import com.aditya.platform.product.service.PlanService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/plans")
public class PlanController {
    private final PlanService planService;

    public PlanController(PlanService planService) { this.planService = planService; }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PlanResponse create(@Valid @RequestBody CreatePlanRequest request) {
        return planService.create(request);
    }

    @GetMapping("/{id}")
    public PlanResponse get(@PathVariable UUID id) {
        return planService.get(id);
    }
}
