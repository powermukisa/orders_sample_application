package com.canals.orders.infrastructure.adapter.persistence;

import com.canals.orders.domain.model.Customer;
import com.canals.orders.domain.port.CustomerRepository;
import com.canals.orders.infrastructure.adapter.persistence.jpa.JpaCustomerRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * Adapter implementing CustomerRepository port
 */
@Component
public class CustomerRepositoryAdapter implements CustomerRepository {
    
    private final JpaCustomerRepository jpaRepository;
    
    public CustomerRepositoryAdapter(JpaCustomerRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }
    
    @Override
    public Optional<Customer> findById(Long id) {
        return jpaRepository.findById(id)
            .map(entity -> entity.toDomain());
    }
}

