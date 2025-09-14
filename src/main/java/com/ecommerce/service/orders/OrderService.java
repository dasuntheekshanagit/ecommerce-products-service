package com.ecommerce.service.orders;

import com.ecommerce.dto.orders.request.CreateOrderRequestDTO;
import com.ecommerce.dto.orders.request.UpdateOrderStatusRequestDTO;
import com.ecommerce.dto.orders.response.OrderResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface OrderService {
    List<OrderResponse> getUserOrders(Long userId);
    Page<OrderResponse> getAllOrders(Pageable pageable);
    OrderResponse createOrder(CreateOrderRequestDTO request);
    OrderResponse getOrderById(Long orderId);
    OrderResponse getUserOrderById(Long userId, Long orderId);
    OrderResponse updateOrderStatus(Long orderId, UpdateOrderStatusRequestDTO request);
    OrderResponse updateUserOrderStatus(Long userId, Long orderId, UpdateOrderStatusRequestDTO request);
    void deleteOrder(Long userId, Long orderId);
    OrderResponse updateOrder(Long orderId, CreateOrderRequestDTO request);
}

