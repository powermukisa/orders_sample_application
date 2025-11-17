package com.canals.orders.domain.port;

import com.canals.orders.domain.model.Order;

import java.util.List;
import java.util.Optional;

/**
 * Port for order data access
 */
public interface OrderRepository {
    Order save(Order order);
    Optional<Order> findById(Long id);
    List<Order> findAll();
}

