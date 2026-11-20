package com.yougotcrushed.casst.services;

import com.yougotcrushed.casst.dtos.WebhookRequest;
import com.yougotcrushed.casst.entities.Order;

import java.util.Optional;

public interface PaymentGateway {
    CheckoutSession createCheckoutSession(Order order);
    Optional<PaymentResult> parseWebhookRequest(WebhookRequest request);
}
