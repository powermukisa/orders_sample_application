package com.canals.orders.application.dto;

import com.canals.orders.domain.model.PaymentInfo;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

/**
 * DTO for payment information
 */
public record PaymentInfoDto(
    @NotBlank(message = "Credit card number is required")
    @Pattern(regexp = "\\d{13,19}", message = "Invalid credit card number")
    String creditCardNumber,
    
    @NotBlank(message = "CVV is required")
    @Pattern(regexp = "\\d{3,4}", message = "Invalid CVV")
    String cvv,
    
    @Min(value = 1, message = "Expiry month must be between 1 and 12")
    @Max(value = 12, message = "Expiry month must be between 1 and 12")
    int expiryMonth,
    
    @Min(value = 2024, message = "Invalid expiry year")
    int expiryYear
) {
    public PaymentInfo toDomain() {
        return new PaymentInfo(creditCardNumber, cvv, expiryMonth, expiryYear);
    }
}

