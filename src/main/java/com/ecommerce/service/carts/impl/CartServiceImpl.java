package com.ecommerce.service.carts.impl;

import com.ecommerce.mapper.carts.CartMapper;
import com.ecommerce.dto.carts.request.AddCartItemRequestDTO;
import com.ecommerce.dto.carts.request.UpdateCartItemRequestDTO;
import com.ecommerce.dto.carts.response.CartItemResponseDTO;
import com.ecommerce.dto.carts.response.CartResponseDTO;
import com.ecommerce.entity.carts.Cart;
import com.ecommerce.entity.carts.CartItem;
import com.ecommerce.exception.ResourceNotFoundException;
import com.ecommerce.repository.carts.CartItemRepository;
import com.ecommerce.repository.carts.CartRepository;
import com.ecommerce.service.carts.CartService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@Service
@Transactional
public class CartServiceImpl implements CartService {

    @Autowired
    CartRepository cartRepository;

    @Autowired
    CartItemRepository cartItemRepository;

    @Autowired
    CartMapper cartMapper;

    @Override
    @Transactional(readOnly = true)
    public CartResponseDTO getCartByUserId(Long userId) {
        log.info("Fetching cart for user: {}", userId);
        Optional<Cart> cartOptional = cartRepository.findByUserIdWithItems(userId);
        if (cartOptional.isEmpty()) {
            log.warn("No cart found for user: {}", userId);
            throw new ResourceNotFoundException("Cart not found for user: " + userId);
        }
        return cartMapper.toResponse(cartOptional.get());
    }

    @Override
    public CartItemResponseDTO addItemToCart(Long userId, AddCartItemRequestDTO request) {
        log.info("Adding item to cart for user: {}, productId: {}", userId, request.getProductId());
        Cart cart = getOrCreateCart(userId);
        Optional<CartItem> existingItem = cartItemRepository.findByCartIdAndProductId(cart.getId(), request.getProductId());
        CartItem cartItem;
        if (existingItem.isPresent()) {
            cartItem = existingItem.get();
            cartItem.setQuantity(cartItem.getQuantity() + request.getQuantity());
            cartItem.setPrice(request.getPrice());
            log.info("Updated existing cart item quantity to: {}", cartItem.getQuantity());
        } else {
            cartItem = new CartItem(cart, request.getProductId(), request.getQuantity(), request.getPrice());
            cart.addItem(cartItem);
            log.info("Added new item to cart");
        }
        CartItem savedItem = cartItemRepository.save(cartItem);
        return cartMapper.toItemResponse(savedItem);
    }

    @Override
    public CartItemResponseDTO updateCartItem(Long userId, Long itemId, UpdateCartItemRequestDTO request) {
        log.info("Updating cart item: {} for user: {}", itemId, userId);
        CartItem cartItem = cartItemRepository.findById(itemId)
                .orElseThrow(() -> new ResourceNotFoundException("Cart item not found with id: " + itemId));
        if (!cartItem.getCart().getUserId().equals(userId)) {
            throw new ResourceNotFoundException("Cart item not found for user: " + userId);
        }
        cartItem.setQuantity(request.getQuantity());
        CartItem updatedItem = cartItemRepository.save(cartItem);
        log.info("Updated cart item quantity to: {}", request.getQuantity());
        return cartMapper.toItemResponse(updatedItem);
    }

    @Override
    public void removeItemFromCart(Long userId, Long itemId) {
        log.info("Removing cart item: {} for user: {}", itemId, userId);
        CartItem cartItem = cartItemRepository.findById(itemId)
                .orElseThrow(() -> new ResourceNotFoundException("Cart item not found with id: " + itemId));
        if (!cartItem.getCart().getUserId().equals(userId)) {
            throw new ResourceNotFoundException("Cart item not found for user: " + userId);
        }
        cartItemRepository.delete(cartItem);
        log.info("Removed cart item: {}", itemId);
    }

    @Override
    public void clearCart(Long userId) {
        log.info("Clearing cart for user: {}", userId);
        Optional<Cart> cartOptional = cartRepository.findByUserId(userId);
        if (cartOptional.isPresent()) {
            Cart cart = cartOptional.get();
            cart.clearItems();
            cartItemRepository.deleteByCartId(cart.getId());
            cartRepository.save(cart);
            log.info("Cleared cart for user: {}", userId);
        } else {
            log.warn("No cart found for user: {}", userId);
        }
    }

    private Cart getOrCreateCart(Long userId) {
        Optional<Cart> cartOptional = cartRepository.findByUserId(userId);
        if (cartOptional.isPresent()) {
            return cartOptional.get();
        } else {
            Cart newCart = Cart.builder()
                    .userId(userId)
                    .items(null)
                    .build();
            return cartRepository.save(newCart);
        }
    }
}

