package com.example.auth_shop.dto;

import com.example.auth_shop.model.CartItem;
import jakarta.persistence.CascadeType;
import jakarta.persistence.OneToMany;
import lombok.Data;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
@Data
public class CartDto {
    private Long id;
    private Set<CartItemDto> items = new HashSet<>();
    private BigDecimal totalAmount;

}
