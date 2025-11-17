package com.canals.orders.infrastructure.adapter.external;

import com.canals.orders.domain.exception.PaymentFailedException;
import com.canals.orders.domain.model.Money;
import com.canals.orders.domain.model.PaymentInfo;
import com.canals.orders.domain.port.PaymentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.UUID;

/**
 * Mock implementation of PaymentService
 * In a real system, this would integrate with Stripe, PayPal, etc.
 */
@Component
public class MockPaymentService implements PaymentService {
    
    private static final Logger log = LoggerFactory.getLogger(MockPaymentService.class);
    
    @Override
    public String processPayment(PaymentInfo paymentInfo, Money amount, String description) {
        String maskedCard = paymentInfo.getMaskedCardNumber();
        
        log.info("============================================================");
        log.info("PROCESSING PAYMENT");
        log.info("Card: {}", maskedCard);
        log.info("Amount: ${}", amount.amount());
        log.info("Description: {}", description);
        log.info("============================================================");
        
        // Mock logic: Simulate payment failure for specific test cases
        
        // If card number ends with "0000", simulate payment failure
        if (paymentInfo.creditCardNumber().endsWith("0000")) {
            log.error("❌ PAYMENT FAILED: Insufficient funds for card {}", maskedCard);
            throw new PaymentFailedException("Payment declined: Insufficient funds");
        }
        
        // If card number ends with "9999", simulate payment gateway error
        if (paymentInfo.creditCardNumber().endsWith("9999")) {
            log.error("❌ PAYMENT FAILED: Gateway error for card {}", maskedCard);
            throw new PaymentFailedException("Payment gateway error: Service temporarily unavailable");
        }
        
        // Check if card is expired (simple check for demonstration)
        int currentYear = java.time.Year.now().getValue();
        if (paymentInfo.expiryYear() < currentYear) {
            log.error("❌ PAYMENT FAILED: Card expired - {}", maskedCard);
            throw new PaymentFailedException("Payment declined: Card expired");
        }
        
        // For all other cases, simulate successful payment
        String transactionId = "txn_" + UUID.randomUUID().toString().substring(0, 8);
        
        log.info("✅ PAYMENT SUCCESSFUL!");
        log.info("Transaction ID: {}", transactionId);
        log.info("Amount charged: ${}", amount.amount());
        log.info("Card: {}", maskedCard);
        log.info("============================================================");
        
        // In real implementation, we would:
        // 1. Call external payment API
        // 2. Handle various error responses
        // 3. Implement retry logic
        // 4. Store transaction details
        // 5. Handle webhooks for async payment confirmation
        
        return transactionId;
    }
}

