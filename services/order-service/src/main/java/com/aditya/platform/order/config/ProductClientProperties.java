package com.aditya.platform.order.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "product-service")
public record ProductClientProperties(String baseUrl) {}
