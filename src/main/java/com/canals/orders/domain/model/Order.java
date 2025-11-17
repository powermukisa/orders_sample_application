package com.canals.orders.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Domain entity representing an order (aggregate root)
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Order {
    private Long id;
    private Long customerId;
    private Address shippingAddress;
    private Long warehouseId;
    private List<OrderItem> items = new ArrayList<>();
    private Money totalAmount;
    private OrderStatus status;
    private String paymentTransactionId;
    private LocalDateTime createdAt;
    
    /**
     * Calculate total amount from order items
     */
    public Money calculateTotal() {
        return items.stream()
            .map(OrderItem::getSubtotal)
            .reduce(Money.zero(), Money::add);
    }
    
    /**
     * Mark order as confirmed with payment transaction
     */
    public void confirm(String transactionId) {
        this.status = OrderStatus.CONFIRMED;
        this.paymentTransactionId = transactionId;
    }
    
    /**
     * Mark order as payment failed
     */
    public void markPaymentFailed() {
        this.status = OrderStatus.PAYMENT_FAILED;
    }
}

