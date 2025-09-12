package com.ecommerce.repository.carts;

import com.ecommerce.entity.carts.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Long> {

    // Find cart item by cart ID and product ID
    @Query("SELECT ci FROM CartItem ci WHERE ci.cart.id = :cartId AND ci.productId = :productId")
    Optional<CartItem> findByCartIdAndProductId(@Param("cartId") Long cartId, @Param("productId") Long productId);

    // Find all cart items by cart ID
    List<CartItem> findByCartId(Long cartId);

    // Delete all cart items by cart ID
    void deleteByCartId(Long cartId);

    // Count items in cart
    long countByCartId(Long cartId);

    // Check if product exists in cart
    @Query("SELECT COUNT(ci) > 0 FROM CartItem ci WHERE ci.cart.id = :cartId AND ci.productId = :productId")
    boolean existsByCartIdAndProductId(@Param("cartId") Long cartId, @Param("productId") Long productId);
}

