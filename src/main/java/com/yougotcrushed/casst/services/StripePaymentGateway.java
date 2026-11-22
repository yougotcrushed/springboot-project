package com.yougotcrushed.casst.services;

import com.stripe.exception.SignatureVerificationException;
import com.stripe.exception.StripeException;
import com.stripe.model.Event;
import com.stripe.model.PaymentIntent;
import com.stripe.model.checkout.Session;
import com.stripe.net.Webhook;
import com.stripe.param.checkout.SessionCreateParams;
import com.yougotcrushed.casst.dtos.WebhookRequest;
import com.yougotcrushed.casst.entities.Order;
import com.yougotcrushed.casst.entities.OrderItem;
import com.yougotcrushed.casst.enums.PaymentStatus;
import com.yougotcrushed.casst.exceptions.PaymentException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;

@Service
public class StripePaymentGateway implements PaymentGateway {
    @Value("${websiteUrl}")
    private String websiteUrl;

    @Value("${stripe.webhookSecretKey}")
    private String webhookSecretKey;

    @Override
    public CheckoutSession createCheckoutSession(Order order) {
        try {
            // Create a checkout session
            var builder = SessionCreateParams.builder()
                    .setMode(SessionCreateParams.Mode.PAYMENT)
                    .setSuccessUrl(websiteUrl + "/checkout-success?order_id=" + order.getId())
                    .setCancelUrl(websiteUrl + "/checkout-cancel")
                    .setPaymentIntentData(createPaymentIntent(order));

            order.getItems().forEach(item -> {
                var lineItem = creatLineItem(item);
                builder.addLineItem(lineItem);
            });

            var session = Session.create(builder.build());
            return new CheckoutSession(session.getUrl());

        }
        catch (StripeException e) {
            System.out.println(e.getMessage());
            throw new PaymentException();
        }
    }

    private static SessionCreateParams.PaymentIntentData createPaymentIntent(Order order) {
        return SessionCreateParams.PaymentIntentData.builder()
                .putMetadata("order_id", order.getId().toString())
                .build();
    }

    @Override
    public Optional<PaymentResult> parseWebhookRequest(WebhookRequest request) {
        try {
            var payload = request.getPayload();
            var signature = request.getHeaders().get("stripe-signature");
            var event = Webhook.constructEvent(payload, signature, webhookSecretKey);

            return switch (event.getType()) {
                case "payment_intent.succeeded" ->
                    // Update order status (PAID)
                    Optional.of(new PaymentResult(extractOrderId(event), PaymentStatus.PAID));

                case "payment_intent.payment_failed" ->
                    // Update order status (FAILED)
                    Optional.of(new PaymentResult(extractOrderId(event), PaymentStatus.FAILED));


                default -> Optional.empty();

            };

        } catch (SignatureVerificationException e) {
            throw new PaymentException("Signature verification failed");
        }

    }

    private Long extractOrderId(Event event) {
        var stripeObject = event.getDataObjectDeserializer().getObject().orElseThrow(
                () -> new PaymentException("Could not deserialize Stripe event, check the SDK and API version.")
        );

        var paymentIntent = (PaymentIntent) stripeObject;
        return Long.valueOf(paymentIntent.getMetadata().get("order_id"));
    }

    private SessionCreateParams.LineItem creatLineItem(OrderItem item) {
        return SessionCreateParams.LineItem.builder()
                .setQuantity(Long.valueOf(item.getQuantity()))
                .setPriceData(creatPriceData(item)).build();
    }

    private SessionCreateParams.LineItem.PriceData creatPriceData(OrderItem item) {
        return SessionCreateParams.LineItem.PriceData.builder()
                .setCurrency("usd")
                .setUnitAmountDecimal(item.getUnitPrice().multiply(BigDecimal.valueOf(100)))
                .setProductData(creatProductData(item))
                .build();
    }

    private SessionCreateParams.LineItem.PriceData.ProductData creatProductData(OrderItem item) {
        return SessionCreateParams.LineItem.PriceData.ProductData.builder()
                .setName(item.getProduct().getName())
                .build();
    }
}
