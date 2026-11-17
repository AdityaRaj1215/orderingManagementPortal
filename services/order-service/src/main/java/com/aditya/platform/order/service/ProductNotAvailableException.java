package com.aditya.platform.order.service;

public class ProductNotAvailableException extends RuntimeException {
    public ProductNotAvailableException(String message) { super(message); }
}
