package com.example.auth_shop.service.cart;

import com.example.auth_shop.exceptions.ProductOutOfStockException;
import com.example.auth_shop.exceptions.ResourceNotFoundException;
import com.example.auth_shop.model.Cart;
import com.example.auth_shop.model.CartItem;
import com.example.auth_shop.model.Product;
import com.example.auth_shop.repository.CartItemRepository;
import com.example.auth_shop.repository.CartRepository;
import com.example.auth_shop.service.product.IProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class CartItemService implements ICartItemService {
    private final CartItemRepository cartItemRepository;
    private final IProductService productService;
    private final ICartService cartService;
    private final CartRepository cartRepository;

    @Override
    public void addItemToCart(Long cartId, Long productId, int quantity) {
        // get the cart
        // get the product
        // check if the product already in the cart
        // if yes ? increase quantity with requested quantity : the initiate a new CartItem entry.
        Cart cart = cartService.getCart(cartId);
        Product product = productService.getProductById(productId);

        CartItem cartItem = cart.getItems()
                .stream()
                .filter(item -> item.getProduct().getId().equals(productId))
                .findFirst().orElse(new CartItem()); // check product already exist in the cart  | if not create new cart

        //wrong logic
//        if (cartItem.getProduct().getInventory() <= 0) { // if inventory in warehouse <= 0  throw exception item out of stock
//            throw new ProductOutOfStockException(product.getName() + product.getBrand() + "is out of stock");
//        } else if (cartItem.getId() == null) {  // add new cart item by product
//            cartItem.setCart(cart);
//            cartItem.setProduct(product);
//            cartItem.setQuantity(quantity);
//            cartItem.setUnitPrice(product.getPrice());
//        } else {
//            cartItem.setQuantity(cartItem.getQuantity() + quantity);
//        }

          // check null  -> !null ? check inventory ? <=0  ? ex  : update
        if (cartItem.getId() == null) {
            cartItem.setCart(cart);
            cartItem.setProduct(product);
            cartItem.setQuantity(quantity);
            cartItem.setUnitPrice(product.getPrice());
        } else if (cartItem.getProduct().getInventory()  <=0 ){
            throw new ProductOutOfStockException(product.getName() + product.getBrand() + "is out of stock");
        }else {
            cartItem.setQuantity(cartItem.getQuantity() + quantity);
        }

        cartItem.setTotalPrice();
        cart.addItem(cartItem);
        cartItemRepository.save(cartItem);
        cartRepository.save(cart);
    }

    @Override
    public void removeItemFromCart(Long cartId, Long productId) {
        Cart cart = cartService.getCart(cartId);
        CartItem itemToRemove = getCartItem(cartId, productId);
        cart.removeItem(itemToRemove);
        if (cart.getItems().isEmpty()) {
            cartService.clearCart(cartId);
        } else {
            cartRepository.save(cart);
        }
    }

    @Override
    public void updateItemQuantity(Long cartId, Long productId, int quantity) {
        Cart cart = cartService.getCart(cartId);
        cart.getItems()
                .stream()
                .filter(item -> item.getProduct().getId().equals(productId))
                .findFirst()
                .ifPresent(item -> {
                    item.setQuantity(quantity);
                    item.setUnitPrice(productService.getProductById(productId).getPrice());
                    item.setTotalPrice();
                });
        BigDecimal totalAmount = cart.getItems().stream()
                .map(CartItem::getTotalPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        cart.setTotalAmount(totalAmount);
        cartRepository.save(cart);
    }

    @Override
    public CartItem getCartItem(Long cartId, Long productId) {
        Cart cart = cartService.getCart(cartId);
        return cart.getItems()
                .stream()
                .filter(item -> item.getProduct().getId().equals(productId))
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("Item not found"));
    }
}
