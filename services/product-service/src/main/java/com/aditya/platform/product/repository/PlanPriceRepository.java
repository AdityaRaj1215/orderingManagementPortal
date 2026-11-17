package com.aditya.platform.product.repository;

import com.aditya.platform.product.domain.PlanPrice;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.UUID;

public interface PlanPriceRepository extends JpaRepository<PlanPrice, UUID> {
    List<PlanPrice> findByPlanIdOrderByVersionDesc(UUID planId);
}
