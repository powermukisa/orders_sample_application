package com.canals.orders.infrastructure.adapter.persistence.entity;

import com.canals.orders.domain.model.Customer;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * JPA Entity for Customer
 */
@Entity
@Table(name = "customers")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CustomerEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String name;
    
    @Column(nullable = false, unique = true)
    private String email;
    
    public Customer toDomain() {
        return new Customer(id, name, email);
    }
    
    public static CustomerEntity fromDomain(Customer customer) {
        return new CustomerEntity(
            customer.getId(),
            customer.getName(),
            customer.getEmail()
        );
    }
}

