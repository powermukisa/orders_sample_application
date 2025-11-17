package com.canals.orders.domain.model;

/**
 * Enum representing order status
 */
public enum OrderStatus {
    PENDING,
    CONFIRMED,
    PAYMENT_FAILED,
    CANCELLED,
    SHIPPED,
    DELIVERED
}

