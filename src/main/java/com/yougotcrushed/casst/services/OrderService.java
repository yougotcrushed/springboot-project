package com.yougotcrushed.casst.services;

import com.yougotcrushed.casst.dtos.OrderDto;
import com.yougotcrushed.casst.exceptions.OrderNotFoundException;
import com.yougotcrushed.casst.mappers.OrderMapper;
import com.yougotcrushed.casst.repositories.OrderRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@AllArgsConstructor
@Service
public class OrderService {
    private final AuthService authService;
    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;

    public List<OrderDto> getAllOrders() {
        var user = authService.getCurrentUser();
        var orders = orderRepository.getOrdersByCustomer(user);

        return orders.stream().map(orderMapper::toDto).toList();
    }

    public OrderDto getOrder(@PathVariable Long orderId) {
        var order = orderRepository.getOrderWithItems(orderId).orElseThrow(OrderNotFoundException::new);

        var user = authService.getCurrentUser();
        if  (!order.isPlacedBy(user)) {
            throw new AccessDeniedException("You are not allowed to access this order");
        }

        return orderMapper.toDto(order);
    }
}
