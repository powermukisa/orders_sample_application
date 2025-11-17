package com.canals.orders.domain.exception;

/**
 * Exception thrown when a customer is not found
 */
public class CustomerNotFoundException extends DomainException {
    public CustomerNotFoundException(Long customerId) {
        super("Customer not found with id: " + customerId);
    }
}

