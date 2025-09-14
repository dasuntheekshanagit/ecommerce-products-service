package com.ecommerce.repository.orders;

import com.ecommerce.entity.orders.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {

    // Find all items for an order
    List<OrderItem> findByOrderId(Long orderId);

    // Delete all items for an order
    void deleteByOrderId(Long orderId);

    // Count items in an order
    long countByOrderId(Long orderId);
}

