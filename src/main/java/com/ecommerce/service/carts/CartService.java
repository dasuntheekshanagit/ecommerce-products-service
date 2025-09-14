package com.ecommerce.service.carts;

import com.ecommerce.dto.carts.request.AddCartItemRequestDTO;
import com.ecommerce.dto.carts.request.UpdateCartItemRequestDTO;
import com.ecommerce.dto.carts.response.CartItemResponseDTO;
import com.ecommerce.dto.carts.response.CartResponseDTO;

public interface CartService {
    CartResponseDTO getCartByUserId(Long userId);
    CartResponseDTO createCart(Long userId);
    CartItemResponseDTO addItemToCart(Long userId, AddCartItemRequestDTO request);
    CartItemResponseDTO updateCartItem(Long userId, Long itemId, UpdateCartItemRequestDTO request);
    void removeItemFromCart(Long userId, Long itemId);
    void clearCart(Long userId);
    void deleteCart(Long userId);
}
