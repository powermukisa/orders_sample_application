package com.canals.orders.application.service;

import com.canals.orders.application.dto.OrderResponse;
import com.canals.orders.domain.model.Order;
import com.canals.orders.domain.port.OrderRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Use case for retrieving all orders
 */
@Service
public class GetAllOrdersUseCase {
    
    private static final Logger log = LoggerFactory.getLogger(GetAllOrdersUseCase.class);
    
    private final OrderRepository orderRepository;
    
    public GetAllOrdersUseCase(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }
    
    @Transactional(readOnly = true)
    public List<OrderResponse> execute() {
        log.info("📋 Retrieving all orders");
        
        List<Order> orders = orderRepository.findAll();
        
        log.info("✓ Found {} order(s)", orders.size());
        
        return orders.stream()
            .map(OrderResponse::fromDomain)
            .toList();
    }
}

