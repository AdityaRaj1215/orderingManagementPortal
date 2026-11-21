package com.aditya.platform.product.repository;


import com.aditya.platform.product.entity.Product;
import com.aditya.platform.product.entity.ProductStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ProductRepository
        extends JpaRepository<Product, UUID> {

    List<Product> findByStatus(ProductStatus status);
}
