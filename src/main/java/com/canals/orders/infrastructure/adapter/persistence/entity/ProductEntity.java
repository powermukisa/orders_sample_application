package com.canals.orders.infrastructure.adapter.persistence.entity;

import com.canals.orders.domain.model.Money;
import com.canals.orders.domain.model.Product;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * JPA Entity for Product
 */
@Entity
@Table(name = "products")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String name;
    
    @Column(length = 500)
    private String description;
    
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal price;
    
    public Product toDomain() {
        return new Product(id, name, description, new Money(price));
    }
    
    public static ProductEntity fromDomain(Product product) {
        return new ProductEntity(
            product.getId(),
            product.getName(),
            product.getDescription(),
            product.getPrice().amount()
        );
    }
}

