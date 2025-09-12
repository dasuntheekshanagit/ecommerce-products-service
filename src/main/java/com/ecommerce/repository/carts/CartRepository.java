package com.ecommerce.repository.carts;

import com.ecommerce.entity.carts.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {

    // Find cart by user ID
    Optional<Cart> findByUserId(Long userId);

    // Find cart with items by user ID
    @Query("SELECT c FROM Cart c LEFT JOIN FETCH c.items WHERE c.userId = :userId")
    Optional<Cart> findByUserIdWithItems(@Param("userId") Long userId);

    // Check if cart exists for user
    boolean existsByUserId(Long userId);

    // Delete cart by user ID
    void deleteByUserId(Long userId);
}

