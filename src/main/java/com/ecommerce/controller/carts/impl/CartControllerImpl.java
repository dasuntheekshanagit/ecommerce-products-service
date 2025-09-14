package com.ecommerce.controller.carts.impl;

import com.ecommerce.controller.AbstractController;
import com.ecommerce.controller.carts.CartController;
import com.ecommerce.dto.ApiResponseDTO;
import com.ecommerce.dto.carts.request.AddCartItemRequestDTO;
import com.ecommerce.dto.carts.request.UpdateCartItemRequestDTO;
import com.ecommerce.dto.carts.response.CartItemResponseDTO;
import com.ecommerce.dto.carts.response.CartResponseDTO;
import com.ecommerce.service.carts.CartService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class CartControllerImpl extends AbstractController implements CartController {

    @Autowired
    CartService cartService;

    public ResponseEntity<ApiResponseDTO<CartResponseDTO>> getCart(Long userId) {

        log.info("GET /v1/cart - userId: {}", userId);
        return ok(() -> cartService.getCartByUserId(userId));
    }

    @Override
    public ResponseEntity<ApiResponseDTO<CartResponseDTO>> createCart(Long userId) {

        log.info("POST /v1/cart - userId: {} (create cart)", userId);
        return created(() -> cartService.createCart(userId));
    }

    public ResponseEntity<ApiResponseDTO<CartItemResponseDTO>> addItemToCart(
            Long userId,
            AddCartItemRequestDTO request) {

        log.info("POST /v1/cart/items - userId: {}, productId: {}", userId, request.getProductId());
        return created(() -> cartService.addItemToCart(userId, request));
    }

    public ResponseEntity<ApiResponseDTO<CartItemResponseDTO>> updateCartItem(
            Long userId,
            Long itemId,
            UpdateCartItemRequestDTO request) {

        log.info("PATCH /v1/cart/items/{} - userId: {}, quantity: {}", itemId, userId, request.getQuantity());
        return ok(() -> cartService.updateCartItem(userId, itemId, request));
    }

    public ResponseEntity<ApiResponseDTO<Void>> removeItemFromCart(
            Long userId,
            Long itemId) {

        log.info("DELETE /v1/cart/items/{} - userId: {}", itemId, userId);
        return noContent(() -> cartService.removeItemFromCart(userId, itemId));
    }

    public ResponseEntity<ApiResponseDTO<Void>> clearCart(
            Long userId) {

        log.info("DELETE /v1/cart - userId: {}", userId);
        return noContent(() -> cartService.clearCart(userId));
    }

    public ResponseEntity<ApiResponseDTO<Void>> deleteCart(Long userId) {
        log.info("DELETE /v1/carts/delete/{} - userId: {}", userId, userId);
        return noContent(() -> cartService.deleteCart(userId));
    }
}
