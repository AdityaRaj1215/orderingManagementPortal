package com.aditya.platform.order.dto;

import java.math.BigDecimal;
import java.util.UUID;

public record ProductPlanResponse(
        UUID id,
        String code,
        String name,
        String description,
        String status,
        UUID priceId,
        Integer priceVersion,
        BigDecimal amount,
        String currency,
        String billingInterval
) {}
