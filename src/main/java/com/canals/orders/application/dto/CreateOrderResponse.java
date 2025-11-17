package com.canals.orders.application.dto;

import java.math.BigDecimal;

/**
 * Response DTO for order creation
 */
public record CreateOrderResponse(
    Long orderId,
    String status,
    Long warehouseId,
    BigDecimal totalAmount,
    int estimatedDeliveryDays
) {
}

