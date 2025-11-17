package com.canals.orders.infrastructure.adapter.persistence;

import com.canals.orders.domain.model.WarehouseInventory;
import com.canals.orders.domain.port.WarehouseInventoryRepository;
import com.canals.orders.infrastructure.adapter.persistence.entity.WarehouseInventoryEntity;
import com.canals.orders.infrastructure.adapter.persistence.jpa.JpaWarehouseInventoryRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Adapter implementing WarehouseInventoryRepository port
 */
@Component
public class WarehouseInventoryRepositoryAdapter implements WarehouseInventoryRepository {
    
    private final JpaWarehouseInventoryRepository jpaRepository;
    
    public WarehouseInventoryRepositoryAdapter(JpaWarehouseInventoryRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }
    
    @Override
    public Optional<WarehouseInventory> findByWarehouseIdAndProductId(Long warehouseId, Long productId) {
        return jpaRepository.findByWarehouseIdAndProductId(warehouseId, productId)
            .map(WarehouseInventoryEntity::toDomain);
    }
    
    @Override
    public List<WarehouseInventory> findByWarehouseId(Long warehouseId) {
        return jpaRepository.findByWarehouseId(warehouseId).stream()
            .map(WarehouseInventoryEntity::toDomain)
            .collect(Collectors.toList());
    }
    
    @Override
    public void save(WarehouseInventory inventory) {
        WarehouseInventoryEntity entity = WarehouseInventoryEntity.fromDomain(inventory);
        jpaRepository.save(entity);
    }
}

