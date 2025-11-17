package com.canals.orders.domain.port;

import com.canals.orders.domain.model.Money;
import com.canals.orders.domain.model.PaymentInfo;

/**
 * Port for payment processing service
 */
public interface PaymentService {
    /**
     * Process a payment
     * @param paymentInfo payment details
     * @param amount amount to charge
     * @param description payment description
     * @return transaction ID if successful
     * @throws PaymentFailedException if payment fails
     */
    String processPayment(PaymentInfo paymentInfo, Money amount, String description);
}

