package com.aditya.platform.order.service;

import com.aditya.platform.order.domain.*;
import com.aditya.platform.order.dto.*;
import com.aditya.platform.order.repository.OrderRepository;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientResponseException;

import java.math.BigDecimal;
import java.util.UUID;

@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final RestClient productRestClient;

    public OrderService(OrderRepository orderRepository, RestClient productRestClient) {
        this.orderRepository = orderRepository;
        this.productRestClient = productRestClient;
    }

    @Transactional
    public OrderResponse create(CreateOrderRequest request, String idempotencyKey) {
        if (idempotencyKey == null || idempotencyKey.isBlank() || idempotencyKey.length() > 128) {
            throw new IllegalArgumentException("A valid Idempotency-Key header is required");
        }

        var existing = orderRepository.findByCustomerIdAndIdempotencyKey(
                request.customerId(), idempotencyKey);
        if (existing.isPresent()) {
            return toResponse(existing.get());
        }

        ProductPlanResponse plan;
        try {
            plan = productRestClient.get()
                    .uri("/api/v1/plans/{id}", request.planId())
                    .retrieve()
                    .onStatus(HttpStatusCode::isError, (req, res) -> {
                        throw new ProductNotAvailableException("Selected plan could not be retrieved");
                    })
                    .body(ProductPlanResponse.class);
        } catch (RestClientResponseException ex) {
            throw new ProductNotAvailableException("Product Service rejected the selected plan");
        }

        if (plan == null || !"ACTIVE".equals(plan.status())) {
            throw new ProductNotAvailableException("Selected plan is not active");
        }

        BigDecimal total = plan.amount().multiply(BigDecimal.valueOf(request.quantity()));
        OrderEntity order = new OrderEntity(
                request.customerId(), idempotencyKey, total, plan.currency());
        order.addItem(new OrderItem(plan.id(), plan.priceId(), plan.name(),
                request.quantity(), plan.amount(), plan.currency()));

        return toResponse(orderRepository.save(order));
    }

    @Transactional(readOnly = true)
    public OrderResponse get(UUID orderId) {
        return orderRepository.findById(orderId)
                .map(this::toResponse)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found: " + orderId));
    }

    private OrderResponse toResponse(OrderEntity order) {
        return new OrderResponse(order.getId(), order.getOrderNumber(), order.getCustomerId(),
                order.getStatus().name(), order.getTotalAmount(), order.getCurrency(),
                order.getCreatedAt());
    }
}
