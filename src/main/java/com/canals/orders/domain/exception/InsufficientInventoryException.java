package com.canals.orders.domain.exception;

/**
 * Exception thrown when there's insufficient inventory to fulfill an order
 */
public class InsufficientInventoryException extends DomainException {
    public InsufficientInventoryException(String message) {
        super(message);
    }
}

