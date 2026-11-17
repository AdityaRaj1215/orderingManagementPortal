package com.aditya.platform.product.service;

import com.aditya.platform.product.domain.*;
import com.aditya.platform.product.dto.*;
import com.aditya.platform.product.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Locale;
import java.util.UUID;

@Service
public class PlanService {
    private final PlanRepository planRepository;
    private final PlanPriceRepository priceRepository;

    public PlanService(PlanRepository planRepository, PlanPriceRepository priceRepository) {
        this.planRepository = planRepository;
        this.priceRepository = priceRepository;
    }

    @Transactional
    public PlanResponse create(CreatePlanRequest request) {
        if (planRepository.findByCode(request.code()).isPresent()) {
            throw new IllegalArgumentException("A plan with this code already exists");
        }

        BillingInterval interval;
        try {
            interval = BillingInterval.valueOf(request.billingInterval().toUpperCase(Locale.ROOT));
        } catch (IllegalArgumentException ex) {
            throw new IllegalArgumentException("billingInterval must be MONTHLY or YEARLY");
        }

        Plan plan = planRepository.save(new Plan(request.code(), request.name(), request.description()));
        PlanPrice price = priceRepository.save(new PlanPrice(
                plan, 1, request.amount(), request.currency(), interval));
        return toResponse(plan, price);
    }

    @Transactional(readOnly = true)
    public PlanResponse get(UUID id) {
        Plan plan = planRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Plan not found: " + id));
        PlanPrice price = priceRepository.findByPlanIdOrderByVersionDesc(id).stream()
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("Price not found for plan: " + id));
        return toResponse(plan, price);
    }

    private PlanResponse toResponse(Plan plan, PlanPrice price) {
        return new PlanResponse(plan.getId(), plan.getCode(), plan.getName(),
                plan.getDescription(), plan.getStatus().name(), price.getId(),
                price.getVersion(), price.getAmount(), price.getCurrency(),
                price.getBillingInterval().name());
    }
}
