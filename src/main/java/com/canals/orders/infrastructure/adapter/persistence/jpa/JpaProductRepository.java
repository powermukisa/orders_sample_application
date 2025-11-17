package com.canals.orders.infrastructure.adapter.persistence.jpa;

import com.canals.orders.infrastructure.adapter.persistence.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Spring Data JPA repository for Product
 */
public interface JpaProductRepository extends JpaRepository<ProductEntity, Long> {
}

