package com.example.auth_shop.controller;


import com.example.auth_shop.dto.OrderDto;
import com.example.auth_shop.exceptions.ResourceNotFoundException;
import com.example.auth_shop.model.Order;
import com.example.auth_shop.response.APIResponse;
import com.example.auth_shop.service.order.IOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("${api.prefix}/orders")
public class OrderController {
    private final IOrderService orderService;

    @PostMapping("/order")
    public ResponseEntity<APIResponse> createOrder(@RequestParam Long userId) {
        try {
            Order order = orderService.placeOrder(userId);
            OrderDto orderDto = orderService.convertToDto(order);
            return ResponseEntity.ok(new APIResponse("Item Order Success", orderDto));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(new APIResponse("Error Occurred", e.getMessage()));
        }
    }

    @GetMapping("/order")
    public ResponseEntity<APIResponse> getOrderById(@RequestParam Long orderId) {
        try {
            OrderDto order = orderService.getOrder(orderId);
            return ResponseEntity.ok(new APIResponse("Success", order));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new APIResponse("Not found!", e.getMessage()));
        }
    }

    @GetMapping("/{userId}/orders")
    public ResponseEntity<APIResponse> getUserOrders(@PathVariable Long userId) {
        try {
            List<OrderDto> order = orderService.getUserOrders(userId);
            return ResponseEntity.ok(new APIResponse("Success", order));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new APIResponse("Not found!", e.getMessage()));
        }
    }


}
