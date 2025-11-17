package com.canals.orders.domain.port;

import com.canals.orders.domain.model.WarehouseInventory;

import java.util.List;
import java.util.Optional;

/**
 * Port for warehouse inventory data access
 */
public interface WarehouseInventoryRepository {
    Optional<WarehouseInventory> findByWarehouseIdAndProductId(Long warehouseId, Long productId);
    List<WarehouseInventory> findByWarehouseId(Long warehouseId);
    void save(WarehouseInventory inventory);
}

