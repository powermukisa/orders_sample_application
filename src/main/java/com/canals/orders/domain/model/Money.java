package com.canals.orders.domain.model;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Value object representing monetary amount
 */
public record Money(BigDecimal amount) {
    
    public Money {
        if (amount == null) {
            throw new IllegalArgumentException("Amount cannot be null");
        }
        // Ensure 2 decimal places
        amount = amount.setScale(2, RoundingMode.HALF_UP);
    }
    
    public Money add(Money other) {
        return new Money(this.amount.add(other.amount));
    }
    
    public Money multiply(int quantity) {
        return new Money(this.amount.multiply(BigDecimal.valueOf(quantity)));
    }
    
    public static Money zero() {
        return new Money(BigDecimal.ZERO);
    }
    
    public static Money of(double value) {
        return new Money(BigDecimal.valueOf(value));
    }
    
    public static Money of(String value) {
        return new Money(new BigDecimal(value));
    }
}

