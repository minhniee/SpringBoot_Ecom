package com.example.auth_shop.service.cart;

import com.example.auth_shop.dto.CartDto;
import com.example.auth_shop.exceptions.ResourceNotFoundException;
import com.example.auth_shop.model.Cart;
import com.example.auth_shop.model.User;
import com.example.auth_shop.repository.CartItemRepository;
import com.example.auth_shop.repository.CartRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

@Service
@RequiredArgsConstructor
public class CartService implements ICartService {

    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final AtomicLong cartIdGenerator = new AtomicLong(0);
    private final ModelMapper modelMapper;

    @Override
    public Cart getCart(Long id) {
        Cart cart = cartRepository.findById(id) // get cart here
                .orElseThrow(() -> new ResourceNotFoundException("Cart not found"));
        BigDecimal totalAmount = cart.getTotalAmount(); // then get totalAmount
        cart.setTotalAmount(totalAmount); // then set its value again ?
        return cartRepository.save(cart) ;
    }

    // if in cart have many goods but user buy some product on cart (not yet processed)
    @Transactional
    @Override
    public void clearCart(Long id) {
    Cart cart = getCart(id);
    cartItemRepository.deleteAllByCartId(id);
    cart.getItems().clear();
    cart.setTotalAmount(BigDecimal.ZERO);
    cartRepository.delete(cart);
    }

    @Override
    public BigDecimal getTotalPrice(Long id) {
        Cart cart = getCart(id);
        return cart.getTotalAmount();
    }

    @Override
    public Cart initializeNewCart(User user) {
       return Optional.ofNullable(getCartByUserId(user.getId()))
               .orElseGet(() -> {
                   Cart cart = new Cart();
                   cart.setUser(user);
                   return cartRepository.save(cart);
               });
    }

    @Override
    public Cart getCartByUserId(Long userId) {
        return cartRepository.findByUserId(userId);
    }


    @Override
    public CartDto convertToCartDto(Cart cart) {
        CartDto dto = modelMapper.map(cart, CartDto.class);
        return dto;
    }
}
