package com.canals.orders.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Domain entity representing an item in an order
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderItem {
    private Long id;
    private Long orderId;
    private Long productId;
    private int quantity;
    private Money priceAtOrder;
    
    /**
     * Calculate subtotal for this line item
     */
    public Money getSubtotal() {
        return priceAtOrder.multiply(quantity);
    }
}

