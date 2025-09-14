package com.ecommerce.repository.orders;

import com.ecommerce.entity.orders.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {

    // Find all payments for an order
    List<Payment> findByOrderId(Long orderId);

    // Delete all payments for an order
    void deleteByOrderId(Long orderId);

    // Count payments for an order
    long countByOrderId(Long orderId);
}

