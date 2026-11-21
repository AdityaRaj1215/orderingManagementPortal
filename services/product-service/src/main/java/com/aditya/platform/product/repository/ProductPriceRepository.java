package com.aditya.platform.product.repository;


import com.aditya.platform.product.entity.ProductPrice;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ProductPriceRepository
        extends JpaRepository<ProductPrice, UUID> {

    List<ProductPrice> findByProductId(UUID productId);
}
