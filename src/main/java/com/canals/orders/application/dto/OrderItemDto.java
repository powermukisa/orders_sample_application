package com.canals.orders.application.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

/**
 * DTO for order item
 */
public record OrderItemDto(
    @NotNull(message = "Product ID is required")
    Long productId,
    
    @Min(value = 1, message = "Quantity must be at least 1")
    int quantity
) {
}

