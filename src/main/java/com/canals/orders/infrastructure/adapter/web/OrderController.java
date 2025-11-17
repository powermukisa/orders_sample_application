package com.canals.orders.infrastructure.adapter.web;

import com.canals.orders.application.dto.CreateOrderRequest;
import com.canals.orders.application.dto.CreateOrderResponse;
import com.canals.orders.application.dto.OrderResponse;
import com.canals.orders.application.service.CreateOrderUseCase;
import com.canals.orders.application.service.GetAllOrdersUseCase;
import com.canals.orders.application.service.GetOrderByIdUseCase;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST controller for order operations
 */
@RestController
@RequestMapping("/orders")
public class OrderController {
    
    private final CreateOrderUseCase createOrderUseCase;
    private final GetAllOrdersUseCase getAllOrdersUseCase;
    private final GetOrderByIdUseCase getOrderByIdUseCase;
    
    public OrderController(
            CreateOrderUseCase createOrderUseCase,
            GetAllOrdersUseCase getAllOrdersUseCase,
            GetOrderByIdUseCase getOrderByIdUseCase) {
        this.createOrderUseCase = createOrderUseCase;
        this.getAllOrdersUseCase = getAllOrdersUseCase;
        this.getOrderByIdUseCase = getOrderByIdUseCase;
    }
    
    /**
     * Create a new order
     */
    @PostMapping
    public ResponseEntity<CreateOrderResponse> createOrder(@Valid @RequestBody CreateOrderRequest request) {
        CreateOrderResponse response = createOrderUseCase.execute(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
    
    /**
     * Get all orders
     */
    @GetMapping
    public ResponseEntity<List<OrderResponse>> getAllOrders() {
        List<OrderResponse> orders = getAllOrdersUseCase.execute();
        return ResponseEntity.ok(orders);
    }
    
    /**
     * Get a specific order by ID
     */
    @GetMapping("/{orderId}")
    public ResponseEntity<OrderResponse> getOrderById(@PathVariable Long orderId) {
        OrderResponse order = getOrderByIdUseCase.execute(orderId);
        return ResponseEntity.ok(order);
    }
}

