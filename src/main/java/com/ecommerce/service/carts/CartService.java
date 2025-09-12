package com.ecommerce.service.carts;

import com.ecommerce.dto.carts.CartMapper;
import com.ecommerce.dto.carts.request.AddCartItemRequest;
import com.ecommerce.dto.carts.request.UpdateCartItemRequest;
import com.ecommerce.dto.carts.response.CartItemResponse;
import com.ecommerce.dto.carts.response.CartResponse;
import com.ecommerce.entity.carts.Cart;
import com.ecommerce.entity.carts.CartItem;
import com.ecommerce.controller.carts.exception.ResourceNotFoundException;
import com.ecommerce.repository.carts.CartItemRepository;
import com.ecommerce.repository.carts.CartRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class CartService {

    private static final Logger logger = LoggerFactory.getLogger(CartService.class);

    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final CartMapper cartMapper;

    @Autowired
    public CartService(CartRepository cartRepository, 
                      CartItemRepository cartItemRepository,
                      CartMapper cartMapper) {
        this.cartRepository = cartRepository;
        this.cartItemRepository = cartItemRepository;
        this.cartMapper = cartMapper;
    }

    @Transactional(readOnly = true)
    public CartResponse getCartByUserId(Long userId) {
        logger.info("Fetching cart for user: {}", userId);

        Optional<Cart> cartOptional = cartRepository.findByUserIdWithItems(userId);
        
        if (cartOptional.isEmpty()) {
            // Create empty cart for user if it doesn't exist
            Cart newCart = new Cart(userId);
            Cart savedCart = cartRepository.save(newCart);
            logger.info("Created new cart for user: {}", userId);
            return cartMapper.toResponse(savedCart);
        }

        return cartMapper.toResponse(cartOptional.get());
    }

    public CartItemResponse addItemToCart(Long userId, AddCartItemRequest request) {
        logger.info("Adding item to cart for user: {}, productId: {}", userId, request.getProductId());

        // Get or create cart for user
        Cart cart = getOrCreateCart(userId);

        // Check if item already exists in cart
        Optional<CartItem> existingItem = cartItemRepository
                .findByCartIdAndProductId(cart.getId(), request.getProductId());

        CartItem cartItem;
        if (existingItem.isPresent()) {
            // Update quantity if item already exists
            cartItem = existingItem.get();
            cartItem.setQuantity(cartItem.getQuantity() + request.getQuantity());
            cartItem.setPrice(request.getPrice()); // Update price in case it changed
            logger.info("Updated existing cart item quantity to: {}", cartItem.getQuantity());
        } else {
            // Create new cart item
            cartItem = new CartItem(cart, request.getProductId(), request.getQuantity(), request.getPrice());
            cart.addItem(cartItem);
            logger.info("Added new item to cart");
        }

        CartItem savedItem = cartItemRepository.save(cartItem);
        return cartMapper.toItemResponse(savedItem);
    }

    public CartItemResponse updateCartItem(Long userId, Long itemId, UpdateCartItemRequest request) {
        logger.info("Updating cart item: {} for user: {}", itemId, userId);

        CartItem cartItem = cartItemRepository.findById(itemId)
                .orElseThrow(() -> new ResourceNotFoundException("Cart item not found with id: " + itemId));

        // Verify the cart item belongs to the user
        if (!cartItem.getCart().getUserId().equals(userId)) {
            throw new ResourceNotFoundException("Cart item not found for user: " + userId);
        }

        cartItem.setQuantity(request.getQuantity());
        CartItem updatedItem = cartItemRepository.save(cartItem);

        logger.info("Updated cart item quantity to: {}", request.getQuantity());
        return cartMapper.toItemResponse(updatedItem);
    }

    public void removeItemFromCart(Long userId, Long itemId) {
        logger.info("Removing cart item: {} for user: {}", itemId, userId);

        CartItem cartItem = cartItemRepository.findById(itemId)
                .orElseThrow(() -> new ResourceNotFoundException("Cart item not found with id: " + itemId));

        // Verify the cart item belongs to the user
        if (!cartItem.getCart().getUserId().equals(userId)) {
            throw new ResourceNotFoundException("Cart item not found for user: " + userId);
        }

        cartItemRepository.delete(cartItem);
        logger.info("Removed cart item: {}", itemId);
    }

    public void clearCart(Long userId) {
        logger.info("Clearing cart for user: {}", userId);

        Optional<Cart> cartOptional = cartRepository.findByUserId(userId);
        if (cartOptional.isPresent()) {
            Cart cart = cartOptional.get();
            cart.clearItems();
            cartItemRepository.deleteByCartId(cart.getId());
            cartRepository.save(cart);
            logger.info("Cleared cart for user: {}", userId);
        } else {
            logger.warn("No cart found for user: {}", userId);
        }
    }

    private Cart getOrCreateCart(Long userId) {
        Optional<Cart> cartOptional = cartRepository.findByUserId(userId);
        
        if (cartOptional.isPresent()) {
            return cartOptional.get();
        } else {
            Cart newCart = new Cart(userId);
            return cartRepository.save(newCart);
        }
    }
}

