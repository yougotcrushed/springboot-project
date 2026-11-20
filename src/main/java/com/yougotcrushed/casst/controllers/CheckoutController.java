package com.yougotcrushed.casst.controllers;

import com.yougotcrushed.casst.dtos.CheckoutRequest;
import com.yougotcrushed.casst.dtos.CheckoutResponse;
import com.yougotcrushed.casst.dtos.WebhookRequest;
import com.yougotcrushed.casst.repositories.OrderRepository;
import com.yougotcrushed.casst.services.CheckoutService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/checkout")
public class CheckoutController {
    private final CheckoutService checkoutService;
    private final OrderRepository orderRepository;


    @PostMapping
    public CheckoutResponse checkout(@Valid @RequestBody CheckoutRequest request) {

            return checkoutService.checkout(request);
    }

    @PostMapping("/webhook")
    public void handleWebhook(
            @RequestHeader Map<String, String> headers,
            @RequestBody String payload
    ) {
        checkoutService.handleWebhookEvent(new WebhookRequest(headers, payload));
    }
}
