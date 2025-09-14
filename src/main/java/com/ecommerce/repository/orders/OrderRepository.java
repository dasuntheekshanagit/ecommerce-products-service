package com.ecommerce.repository.orders;

import com.ecommerce.entity.orders.Order;
import com.ecommerce.entity.orders.OrderStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    // Find orders by user ID
    List<Order> findByUserIdOrderByCreatedAtDesc(Long userId);

    // Find orders by user ID with pagination
    Page<Order> findByUserIdOrderByCreatedAtDesc(Long userId, Pageable pageable);

    // Find order with items and payments
    @Query("SELECT o FROM Order o LEFT JOIN FETCH o.items LEFT JOIN FETCH o.payments WHERE o.id = :orderId")
    Optional<Order> findByIdWithItemsAndPayments(@Param("orderId") Long orderId);

    // Find order by ID and user ID
    Optional<Order> findByIdAndUserId(Long id, Long userId);

    // Find orders by status
    List<Order> findByStatusOrderByCreatedAtDesc(OrderStatus status);

    // Find all orders with pagination (for admin)
    Page<Order> findAllByOrderByCreatedAtDesc(Pageable pageable);

    // Count orders by user
    long countByUserId(Long userId);

    // Count orders by status
    long countByStatus(OrderStatus status);
}

