package com.canals.orders.domain.port;

import com.canals.orders.domain.model.Product;

import java.util.List;
import java.util.Optional;

/**
 * Port for product data access
 */
public interface ProductRepository {
    Optional<Product> findById(Long id);
    List<Product> findAllById(List<Long> ids);
}

