package com.aditya.platform.order.controller;

import com.aditya.platform.order.dto.*;
import com.aditya.platform.order.service.OrderService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/orders")
public class OrderController {
    private final OrderService orderService;

    public OrderController(OrderService orderService) { this.orderService = orderService; }

    @PostMapping
    @ResponseStatus(HttpStatus.ACCEPTED)
    public OrderResponse create(@Valid @RequestBody CreateOrderRequest request,
                                @RequestHeader("Idempotency-Key") String idempotencyKey) {
        return orderService.create(request, idempotencyKey);
    }

    @GetMapping("/{orderId}")
    public OrderResponse get(@PathVariable UUID orderId) {
        return orderService.get(orderId);
    }
}
