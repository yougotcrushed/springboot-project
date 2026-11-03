package com.yougotcrushed.casst;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

//@Service
public class OrderService {
    private  PaymentService paymentService;

    public OrderService(PaymentService paymentService) {
        this.paymentService = paymentService;
    }
    public void placeOrder() {
//        var paymentService = new StripePaymentService();

        paymentService.processPayment(10);
    }
}
