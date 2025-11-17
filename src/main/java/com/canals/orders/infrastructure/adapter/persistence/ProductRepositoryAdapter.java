package com.canals.orders.infrastructure.adapter.persistence;

import com.canals.orders.domain.model.Product;
import com.canals.orders.domain.port.ProductRepository;
import com.canals.orders.infrastructure.adapter.persistence.jpa.JpaProductRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Adapter implementing ProductRepository port
 */
@Component
public class ProductRepositoryAdapter implements ProductRepository {
    
    private final JpaProductRepository jpaRepository;
    
    public ProductRepositoryAdapter(JpaProductRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }
    
    @Override
    public Optional<Product> findById(Long id) {
        return jpaRepository.findById(id)
            .map(entity -> entity.toDomain());
    }
    
    @Override
    public List<Product> findAllById(List<Long> ids) {
        return jpaRepository.findAllById(ids).stream()
            .map(entity -> entity.toDomain())
            .collect(Collectors.toList());
    }
}

