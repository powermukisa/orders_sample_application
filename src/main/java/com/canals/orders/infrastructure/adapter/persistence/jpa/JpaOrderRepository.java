package com.canals.orders.infrastructure.adapter.persistence.jpa;

import com.canals.orders.infrastructure.adapter.persistence.entity.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Spring Data JPA repository for Order
 */
public interface JpaOrderRepository extends JpaRepository<OrderEntity, Long> {
}

