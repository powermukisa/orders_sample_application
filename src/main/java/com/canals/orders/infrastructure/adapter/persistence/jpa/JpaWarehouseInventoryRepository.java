package com.canals.orders.infrastructure.adapter.persistence.jpa;

import com.canals.orders.infrastructure.adapter.persistence.entity.WarehouseInventoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data JPA repository for Warehouse Inventory
 */
public interface JpaWarehouseInventoryRepository extends JpaRepository<WarehouseInventoryEntity, Long> {
    Optional<WarehouseInventoryEntity> findByWarehouseIdAndProductId(Long warehouseId, Long productId);
    List<WarehouseInventoryEntity> findByWarehouseId(Long warehouseId);
}

