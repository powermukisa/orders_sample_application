package com.canals.orders.application.service;

import com.canals.orders.application.dto.OrderResponse;
import com.canals.orders.domain.exception.DomainException;
import com.canals.orders.domain.model.Order;
import com.canals.orders.domain.port.OrderRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Use case for retrieving a single order by ID
 */
@Service
public class GetOrderByIdUseCase {
    
    private static final Logger log = LoggerFactory.getLogger(GetOrderByIdUseCase.class);
    
    private final OrderRepository orderRepository;
    
    public GetOrderByIdUseCase(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }
    
    @Transactional(readOnly = true)
    public OrderResponse execute(Long orderId) {
        log.info("📋 Retrieving order with ID: {}", orderId);
        
        Order order = orderRepository.findById(orderId)
            .orElseThrow(() -> new OrderNotFoundException(orderId));
        
        log.info("✓ Order found: {} (Status: {})", order.getId(), order.getStatus());
        
        return OrderResponse.fromDomain(order);
    }
    
    /**
     * Exception thrown when order is not found
     */
    public static class OrderNotFoundException extends DomainException {
        public OrderNotFoundException(Long orderId) {
            super("Order not found with id: " + orderId);
        }
    }
}

