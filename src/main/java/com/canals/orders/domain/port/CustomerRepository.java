package com.canals.orders.domain.port;

import com.canals.orders.domain.model.Customer;

import java.util.Optional;

/**
 * Port for customer data access
 */
public interface CustomerRepository {
    Optional<Customer> findById(Long id);
}

