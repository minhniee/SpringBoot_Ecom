package com.example.auth_shop.service.cart;

import com.example.auth_shop.dto.CartDto;
import com.example.auth_shop.model.Cart;
import com.example.auth_shop.model.User;

import java.math.BigDecimal;

public interface ICartService {
    Cart getCart(Long id);
    void clearCart(Long id);
    BigDecimal getTotalPrice(Long id);

    Cart initializeNewCart(User user);

    Cart getCartByUserId(Long userId);

    CartDto convertToCartDto(Cart cart);
}
