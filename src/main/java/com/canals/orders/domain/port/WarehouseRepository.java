package com.canals.orders.domain.port;

import com.canals.orders.domain.model.Warehouse;

import java.util.List;
import java.util.Optional;

/**
 * Port for warehouse data access
 */
public interface WarehouseRepository {
    Optional<Warehouse> findById(Long id);
    List<Warehouse> findAll();
}

