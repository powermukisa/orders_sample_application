package com.canals.orders.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Domain entity representing inventory at a warehouse
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class WarehouseInventory {
    private Long id;
    private Long warehouseId;
    private Long productId;
    private int quantity;
    
    /**
     * Check if there's sufficient stock for the requested quantity
     */
    public boolean hasSufficientStock(int requestedQuantity) {
        return this.quantity >= requestedQuantity;
    }
    
    /**
     * Reduce inventory by the specified quantity
     */
    public void reduceStock(int amount) {
        if (amount > this.quantity) {
            throw new IllegalStateException(
                "Cannot reduce stock by " + amount + " when only " + this.quantity + " available"
            );
        }
        this.quantity -= amount;
    }
}

