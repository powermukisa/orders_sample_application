package com.canals.orders.infrastructure.adapter.persistence.entity;

import com.canals.orders.domain.model.Money;
import com.canals.orders.domain.model.OrderItem;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * JPA Entity for Order Item
 */
@Entity
@Table(name = "order_items")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderItemEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false)
    private OrderEntity order;
    
    @Column(nullable = false)
    private Long productId;
    
    @Column(nullable = false)
    private Integer quantity;
    
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal priceAtOrder;
    
    public OrderItem toDomain() {
        OrderItem item = new OrderItem();
        item.setId(id);
        item.setOrderId(order != null ? order.getId() : null);
        item.setProductId(productId);
        item.setQuantity(quantity);
        item.setPriceAtOrder(new Money(priceAtOrder));
        return item;
    }
    
    public static OrderItemEntity fromDomain(OrderItem item, OrderEntity orderEntity) {
        OrderItemEntity entity = new OrderItemEntity();
        entity.setId(item.getId());
        entity.setOrder(orderEntity);
        entity.setProductId(item.getProductId());
        entity.setQuantity(item.getQuantity());
        entity.setPriceAtOrder(item.getPriceAtOrder().amount());
        return entity;
    }
}

