package com.canals.orders.domain.model;

/**
 * Value object representing payment information
 */
public record PaymentInfo(
    String creditCardNumber,
    String cvv,
    int expiryMonth,
    int expiryYear
) {
    public PaymentInfo {
        if (creditCardNumber == null || creditCardNumber.isBlank()) {
            throw new IllegalArgumentException("Credit card number cannot be blank");
        }
        if (expiryMonth < 1 || expiryMonth > 12) {
            throw new IllegalArgumentException("Invalid expiry month");
        }
    }
    
    /**
     * Get masked card number for display purposes
     */
    public String getMaskedCardNumber() {
        if (creditCardNumber.length() < 4) {
            return "****";
        }
        return "****" + creditCardNumber.substring(creditCardNumber.length() - 4);
    }
}

