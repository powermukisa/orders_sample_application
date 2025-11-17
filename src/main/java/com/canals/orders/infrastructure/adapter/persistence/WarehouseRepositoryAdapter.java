package com.canals.orders.infrastructure.adapter.persistence;

import com.canals.orders.domain.model.Warehouse;
import com.canals.orders.domain.port.WarehouseRepository;
import com.canals.orders.infrastructure.adapter.persistence.jpa.JpaWarehouseRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Adapter implementing WarehouseRepository port
 */
@Component
public class WarehouseRepositoryAdapter implements WarehouseRepository {
    
    private final JpaWarehouseRepository jpaRepository;
    
    public WarehouseRepositoryAdapter(JpaWarehouseRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }
    
    @Override
    public Optional<Warehouse> findById(Long id) {
        return jpaRepository.findById(id)
            .map(entity -> entity.toDomain());
    }
    
    @Override
    public List<Warehouse> findAll() {
        return jpaRepository.findAll().stream()
            .map(entity -> entity.toDomain())
            .collect(Collectors.toList());
    }
}

