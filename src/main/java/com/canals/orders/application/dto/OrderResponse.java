package com.canals.orders.application.dto;

import com.canals.orders.domain.model.Order;
import com.canals.orders.domain.model.OrderItem;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Response DTO for order details
 */
public record OrderResponse(
    Long orderId,
    Long customerId,
    AddressDto shippingAddress,
    Long warehouseId,
    List<OrderItemResponse> items,
    BigDecimal totalAmount,
    String status,
    String paymentTransactionId,
    LocalDateTime createdAt
) {
    public static OrderResponse fromDomain(Order order) {
        AddressDto addressDto = new AddressDto(
            order.getShippingAddress().street(),
            order.getShippingAddress().city(),
            order.getShippingAddress().state(),
            order.getShippingAddress().zipCode(),
            order.getShippingAddress().country()
        );
        
        List<OrderItemResponse> items = order.getItems().stream()
            .map(OrderItemResponse::fromDomain)
            .toList();
        
        return new OrderResponse(
            order.getId(),
            order.getCustomerId(),
            addressDto,
            order.getWarehouseId(),
            items,
            order.getTotalAmount().amount(),
            order.getStatus().name(),
            order.getPaymentTransactionId(),
            order.getCreatedAt()
        );
    }
    
    /**
     * Nested record for order item details
     */
    public record OrderItemResponse(
        Long itemId,
        Long productId,
        int quantity,
        BigDecimal priceAtOrder,
        BigDecimal subtotal
    ) {
        public static OrderItemResponse fromDomain(OrderItem item) {
            return new OrderItemResponse(
                item.getId(),
                item.getProductId(),
                item.getQuantity(),
                item.getPriceAtOrder().amount(),
                item.getSubtotal().amount()
            );
        }
    }
}

