package com.aditya.platform.order.dto;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

public record OrderResponse(
        UUID orderId,
        String orderNumber,
        UUID customerId,
        String status,
        BigDecimal totalAmount,
        String currency,
        Instant createdAt
) {}
