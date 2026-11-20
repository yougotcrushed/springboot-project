package com.yougotcrushed.casst.controllers;

import com.yougotcrushed.casst.dtos.ErrorDto;
import com.yougotcrushed.casst.dtos.OrderDto;
import com.yougotcrushed.casst.exceptions.OrderNotFoundException;
import com.yougotcrushed.casst.mappers.OrderMapper;
import com.yougotcrushed.casst.repositories.OrderRepository;
import com.yougotcrushed.casst.services.AuthService;
import com.yougotcrushed.casst.services.OrderService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/orders")
public class OrderController {
    private final AuthService authService;
    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;
    private final OrderService orderService;

    @GetMapping
    public List<OrderDto> getAllOrders() {
        return orderService.getAllOrders();

    }

    @GetMapping("/{orderId}")
    public OrderDto getOrder(@PathVariable("orderId") Long orderId) {
        return orderService.getOrder(orderId);

    }
}
