package com.canals.orders.application.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;

/**
 * Request DTO for creating an order
 */
public record CreateOrderRequest(
    @NotNull(message = "Customer ID is required")
    Long customerId,
    
    @NotNull(message = "Shipping address is required")
    @Valid
    AddressDto shippingAddress,
    
    @NotEmpty(message = "Order must have at least one item")
    @Valid
    List<OrderItemDto> items,
    
    @NotNull(message = "Payment information is required")
    @Valid
    PaymentInfoDto paymentInfo
) {
}

