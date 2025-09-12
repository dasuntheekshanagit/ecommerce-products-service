package com.ecommerce.controller.carts.impl;

import com.ecommerce.controller.carts.CartController;
import com.ecommerce.dto.carts.request.AddCartItemRequest;
import com.ecommerce.dto.carts.request.UpdateCartItemRequest;
import com.ecommerce.dto.carts.response.CartItemResponse;
import com.ecommerce.dto.carts.response.CartResponse;
import com.ecommerce.service.carts.CartService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class CartControllerImpl implements CartController {

    private final CartService cartService;

    @Autowired
    public CartControllerImpl(CartService cartService) {
        this.cartService = cartService;
    }


    public ResponseEntity<CartResponse> getCart(Long userId) {

        log.info("GET /v1/cart - userId: {}", userId);

        CartResponse response = cartService.getCartByUserId(userId);
        return ResponseEntity.ok(response);
    }

    public ResponseEntity<CartItemResponse> addItemToCart(
            Long userId,
            AddCartItemRequest request) {

        log.info("POST /v1/cart/items - userId: {}, productId: {}", userId, request.getProductId());

        CartItemResponse response = cartService.addItemToCart(userId, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    public ResponseEntity<CartItemResponse> updateCartItem(
            Long userId,
            Long itemId,
            UpdateCartItemRequest request) {

        log.info("PATCH /v1/cart/items/{} - userId: {}, quantity: {}", itemId, userId, request.getQuantity());

        CartItemResponse response = cartService.updateCartItem(userId, itemId, request);
        return ResponseEntity.ok(response);
    }


    public ResponseEntity<Void> removeItemFromCart(
            Long userId,
            Long itemId) {

        log.info("DELETE /v1/cart/items/{} - userId: {}", itemId, userId);

        cartService.removeItemFromCart(userId, itemId);
        return ResponseEntity.noContent().build();
    }


    public ResponseEntity<Void> clearCart(
            Long userId) {

        log.info("DELETE /v1/cart - userId: {}", userId);

        cartService.clearCart(userId);
        return ResponseEntity.noContent().build();
    }
}

