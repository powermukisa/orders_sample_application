package com.canals.orders.infrastructure.adapter.web;

import com.canals.orders.application.dto.CreateOrderRequest;
import com.canals.orders.application.dto.CreateOrderResponse;
import com.canals.orders.application.service.CreateOrderUseCase;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST controller for order operations
 */
@RestController
@RequestMapping("/orders")
public class OrderController {
    
    private final CreateOrderUseCase createOrderUseCase;
    
    public OrderController(CreateOrderUseCase createOrderUseCase) {
        this.createOrderUseCase = createOrderUseCase;
    }
    
    @PostMapping
    public ResponseEntity<CreateOrderResponse> createOrder(@Valid @RequestBody CreateOrderRequest request) {
        CreateOrderResponse response = createOrderUseCase.execute(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}

