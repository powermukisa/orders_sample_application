package com.canals.orders.domain.exception;

/**
 * Exception thrown when a product is not found
 */
public class ProductNotFoundException extends DomainException {
    public ProductNotFoundException(Long productId) {
        super("Product not found with id: " + productId);
    }
}

