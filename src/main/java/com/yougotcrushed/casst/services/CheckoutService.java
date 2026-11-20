package com.yougotcrushed.casst.services;

import com.yougotcrushed.casst.dtos.CheckoutRequest;
import com.yougotcrushed.casst.dtos.CheckoutResponse;
import com.yougotcrushed.casst.dtos.WebhookRequest;
import com.yougotcrushed.casst.entities.Order;
import com.yougotcrushed.casst.exceptions.CartEmptyException;
import com.yougotcrushed.casst.exceptions.CartNotFoundException;
import com.yougotcrushed.casst.exceptions.PaymentException;
import com.yougotcrushed.casst.repositories.CartRepository;
import com.yougotcrushed.casst.repositories.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class CheckoutService {
    private final CartRepository cartRepository;
    private final OrderRepository orderRepository;
    private final AuthService authService;
    private final CartService cartService;
    private final PaymentGateway paymentGateway;


    @Transactional
    public CheckoutResponse checkout(CheckoutRequest request) {
        var cart = cartRepository.getCartWithItems(request.getCartId()).orElse(null);
        if (cart == null) {
            throw new CartNotFoundException();
        }

        if (cart.isEmpty()) {
            throw new CartEmptyException();
        }

        var order = Order.fromCart(cart, authService.getCurrentUser());

        orderRepository.save(order);

        try {
            var session = paymentGateway.createCheckoutSession(order);

            cartService.clearCart(cart.getId());

            return new CheckoutResponse(order.getId(), session.getCheckoutUrl());
        }
        catch (PaymentException ex) {
            orderRepository.delete(order);
            throw ex;
        }

    }

    public void handleWebhookEvent(WebhookRequest request) {
        paymentGateway.parseWebhookRequest(request).ifPresent(paymentResult -> {
            var order = orderRepository.findById(paymentResult.getOrderId()).orElseThrow();
            order.setStatus(paymentResult.getPaymentStatus());
            orderRepository.save(order);
        });
    }
}
