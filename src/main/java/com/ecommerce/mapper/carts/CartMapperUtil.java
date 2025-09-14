package com.ecommerce.mapper.carts;

import com.ecommerce.entity.carts.CartItem;

import java.math.BigDecimal;
import java.util.List;

public class CartMapperUtil {
    public static BigDecimal calculateTotalPrice(List<CartItem> items) {
        return items.stream()
                .map(CartItem::getTotalPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}

