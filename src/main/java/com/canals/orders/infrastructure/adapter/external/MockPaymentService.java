package com.canals.orders.infrastructure.adapter.external;

import com.canals.orders.domain.exception.PaymentFailedException;
import com.canals.orders.domain.model.Money;
import com.canals.orders.domain.model.PaymentInfo;
import com.canals.orders.domain.port.PaymentService;
import org.springframework.stereotype.Component;

import java.util.UUID;

/**
 * Mock implementation of PaymentService
 * In a real system, this would integrate with Stripe, PayPal, etc.
 */
@Component
public class MockPaymentService implements PaymentService {
    
    @Override
    public String processPayment(PaymentInfo paymentInfo, Money amount, String description) {
        // Mock logic: Simulate payment failure for specific test cases
        
        // If card number ends with "0000", simulate payment failure
        if (paymentInfo.creditCardNumber().endsWith("0000")) {
            throw new PaymentFailedException("Payment declined: Insufficient funds");
        }
        
        // If card number ends with "9999", simulate payment gateway error
        if (paymentInfo.creditCardNumber().endsWith("9999")) {
            throw new PaymentFailedException("Payment gateway error: Service temporarily unavailable");
        }
        
        // Check if card is expired (simple check for demonstration)
        int currentYear = java.time.Year.now().getValue();
        if (paymentInfo.expiryYear() < currentYear) {
            throw new PaymentFailedException("Payment declined: Card expired");
        }
        
        // For all other cases, simulate successful payment
        String transactionId = "txn_" + UUID.randomUUID().toString().substring(0, 8);
        
        // In real implementation, we would:
        // 1. Call external payment API
        // 2. Handle various error responses
        // 3. Implement retry logic
        // 4. Store transaction details
        // 5. Handle webhooks for async payment confirmation
        
        return transactionId;
    }
}

