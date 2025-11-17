package com.canals.orders.infrastructure.adapter.persistence.entity;

import com.canals.orders.domain.model.Address;
import com.canals.orders.domain.model.Money;
import com.canals.orders.domain.model.Order;
import com.canals.orders.domain.model.OrderStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * JPA Entity for Order
 */
@Entity
@Table(name = "orders")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private Long customerId;
    
    @Column(nullable = false)
    private String shippingStreet;
    
    @Column(nullable = false)
    private String shippingCity;
    
    private String shippingState;
    
    @Column(nullable = false)
    private String shippingZipCode;
    
    @Column(nullable = false)
    private String shippingCountry;
    
    @Column(nullable = false)
    private Long warehouseId;
    
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderItemEntity> items = new ArrayList<>();
    
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal totalAmount;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private OrderStatus status;
    
    private String paymentTransactionId;
    
    @Column(nullable = false)
    private LocalDateTime createdAt;
    
    public Order toDomain() {
        Address shippingAddress = new Address(
            shippingStreet,
            shippingCity,
            shippingState,
            shippingZipCode,
            shippingCountry
        );
        
        Order order = new Order();
        order.setId(id);
        order.setCustomerId(customerId);
        order.setShippingAddress(shippingAddress);
        order.setWarehouseId(warehouseId);
        order.setItems(items.stream().map(OrderItemEntity::toDomain).collect(Collectors.toList()));
        order.setTotalAmount(new Money(totalAmount));
        order.setStatus(status);
        order.setPaymentTransactionId(paymentTransactionId);
        order.setCreatedAt(createdAt);
        
        return order;
    }
    
    public static OrderEntity fromDomain(Order order) {
        OrderEntity entity = new OrderEntity();
        entity.setId(order.getId());
        entity.setCustomerId(order.getCustomerId());
        entity.setShippingStreet(order.getShippingAddress().street());
        entity.setShippingCity(order.getShippingAddress().city());
        entity.setShippingState(order.getShippingAddress().state());
        entity.setShippingZipCode(order.getShippingAddress().zipCode());
        entity.setShippingCountry(order.getShippingAddress().country());
        entity.setWarehouseId(order.getWarehouseId());
        entity.setTotalAmount(order.getTotalAmount().amount());
        entity.setStatus(order.getStatus());
        entity.setPaymentTransactionId(order.getPaymentTransactionId());
        entity.setCreatedAt(order.getCreatedAt());
        
        List<OrderItemEntity> itemEntities = order.getItems().stream()
            .map(item -> OrderItemEntity.fromDomain(item, entity))
            .collect(Collectors.toList());
        entity.setItems(itemEntities);
        
        return entity;
    }
}

