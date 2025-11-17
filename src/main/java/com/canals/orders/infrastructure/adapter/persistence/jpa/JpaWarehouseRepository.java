package com.canals.orders.infrastructure.adapter.persistence.jpa;

import com.canals.orders.infrastructure.adapter.persistence.entity.WarehouseEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Spring Data JPA repository for Warehouse
 */
public interface JpaWarehouseRepository extends JpaRepository<WarehouseEntity, Long> {
}

