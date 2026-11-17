package com.aditya.platform.product.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record CreatePlanRequest(
        @NotBlank @Size(max = 50) String code,
        @NotBlank @Size(max = 150) String name,
        String description,
        @NotNull @DecimalMin("0.00") BigDecimal amount,
        @NotBlank @Pattern(regexp = "^[A-Za-z]{3}$") String currency,
        @NotBlank String billingInterval
) {}
