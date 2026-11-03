package com.yougotcrushed.casst;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Value("${Payment.stripe}")
    private boolean stripeMethod;

    @Value("${Payment.paypal}")
    private boolean paypalMethod;

    @Bean
    public PaymentService stripe(){
        return new StripePaymentService();
    }

    @Bean
    public PaymentService paypal() {
        return new PaypalPaymentService();
    }

    @Bean
    public NotificationService User() {return new EmailNotificationService();}

    @Bean
    public OrderService orderService() {
        if (stripeMethod == true)

            return new OrderService(stripe());
        else return new OrderService(paypal());
    }
}
