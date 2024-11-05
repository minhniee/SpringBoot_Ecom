package com.example.auth_shop.dto;

import com.example.auth_shop.enums.OrderStatus;
import com.example.auth_shop.model.OrderItem;
import lombok.Data;
import org.springframework.cglib.core.Local;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class OrderDto {
    private Long id;
    private Long userId;
    private LocalDateTime orderDate;
    private BigDecimal orderAmount;
    private OrderStatus status;
    private List<OrderItemDto > items;
}
