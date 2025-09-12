package com.ecommerce.dto.carts;

import com.ecommerce.dto.carts.response.CartItemResponse;
import com.ecommerce.dto.carts.response.CartResponse;
import com.ecommerce.entity.carts.Cart;
import com.ecommerce.entity.carts.CartItem;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class CartMapper {

    public CartResponse toResponse(Cart cart) {
        if (cart == null) {
            return null;
        }

        List<CartItemResponse> itemResponses = cart.getItems()
                .stream()
                .map(this::toItemResponse)
                .collect(Collectors.toList());

        Integer totalItems = cart.getTotalItems();
        BigDecimal totalPrice = calculateTotalPrice(cart.getItems());

        return new CartResponse(
                cart.getId(),
                cart.getUserId(),
                itemResponses,
                totalItems,
                totalPrice,
                cart.getCreatedAt(),
                cart.getUpdatedAt()
        );
    }

    public CartItemResponse toItemResponse(CartItem item) {
        if (item == null) {
            return null;
        }

        return new CartItemResponse(
                item.getId(),
                item.getProductId(),
                item.getQuantity(),
                item.getPrice(),
                item.getTotalPrice()
        );
    }

    private BigDecimal calculateTotalPrice(List<CartItem> items) {
        return items.stream()
                .map(CartItem::getTotalPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}

