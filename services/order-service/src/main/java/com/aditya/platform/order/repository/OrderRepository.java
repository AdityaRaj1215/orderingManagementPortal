package com.aditya.platform.order.repository;

import com.aditya.platform.order.domain.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import java.util.UUID;

public interface OrderRepository extends JpaRepository<OrderEntity, UUID> {
    Optional<OrderEntity> findByCustomerIdAndIdempotencyKey(UUID customerId, String idempotencyKey);
}
