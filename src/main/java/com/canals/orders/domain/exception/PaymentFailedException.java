package com.canals.orders.domain.exception;

/**
 * Exception thrown when payment processing fails
 */
public class PaymentFailedException extends DomainException {
    public PaymentFailedException(String message) {
        super(message);
    }
    
    public PaymentFailedException(String message, Throwable cause) {
        super(message, cause);
    }
}

