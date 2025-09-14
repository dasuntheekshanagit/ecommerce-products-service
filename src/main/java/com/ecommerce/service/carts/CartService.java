package com.ecommerce.service.carts;

import com.ecommerce.dto.carts.request.AddCartItemRequest;
import com.ecommerce.dto.carts.request.UpdateCartItemRequest;
import com.ecommerce.dto.carts.response.CartItemResponse;
import com.ecommerce.dto.carts.response.CartResponse;

public interface CartService {
    CartResponse getCartByUserId(Long userId);
    CartItemResponse addItemToCart(Long userId, AddCartItemRequest request);
    CartItemResponse updateCartItem(Long userId, Long itemId, UpdateCartItemRequest request);
    void removeItemFromCart(Long userId, Long itemId);
    void clearCart(Long userId);
}

