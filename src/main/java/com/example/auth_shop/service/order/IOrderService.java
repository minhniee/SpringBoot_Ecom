package com.example.auth_shop.service.order;

import com.example.auth_shop.dto.OrderDto;
import com.example.auth_shop.model.Order;

import java.util.List;

public interface IOrderService {
    Order placeOrder(Long userId);
    OrderDto getOrder(Long orderId);
    List<OrderDto> getUserOrders(Long userId);

    OrderDto convertToDto(Order order);
}
