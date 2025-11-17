package com.canals.orders.infrastructure.adapter.persistence;

import com.canals.orders.domain.model.Order;
import com.canals.orders.domain.port.OrderRepository;
import com.canals.orders.infrastructure.adapter.persistence.entity.OrderEntity;
import com.canals.orders.infrastructure.adapter.persistence.jpa.JpaOrderRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * Adapter implementing OrderRepository port
 */
@Component
public class OrderRepositoryAdapter implements OrderRepository {
    
    private final JpaOrderRepository jpaRepository;
    
    public OrderRepositoryAdapter(JpaOrderRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }
    
    @Override
    public Order save(Order order) {
        OrderEntity entity = OrderEntity.fromDomain(order);
        OrderEntity saved = jpaRepository.save(entity);
        return saved.toDomain();
    }
    
    @Override
    public Optional<Order> findById(Long id) {
        return jpaRepository.findById(id)
            .map(OrderEntity::toDomain);
    }
}

