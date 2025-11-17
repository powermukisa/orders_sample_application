package com.canals.orders.infrastructure.adapter.persistence.entity;

import com.canals.orders.domain.model.WarehouseInventory;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * JPA Entity for Warehouse Inventory
 */
@Entity
@Table(name = "warehouse_inventory")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class WarehouseInventoryEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private Long warehouseId;
    
    @Column(nullable = false)
    private Long productId;
    
    @Column(nullable = false)
    private Integer quantity;
    
    public WarehouseInventory toDomain() {
        return new WarehouseInventory(id, warehouseId, productId, quantity);
    }
    
    public static WarehouseInventoryEntity fromDomain(WarehouseInventory inventory) {
        return new WarehouseInventoryEntity(
            inventory.getId(),
            inventory.getWarehouseId(),
            inventory.getProductId(),
            inventory.getQuantity()
        );
    }
}

