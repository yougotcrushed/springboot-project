package com.yougotcrushed.casst.services;

import com.yougotcrushed.casst.enums.PaymentStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class PaymentResult {
    private Long orderId;
    private PaymentStatus paymentStatus;
}
