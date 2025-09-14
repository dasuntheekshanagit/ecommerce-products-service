package com.ecommerce.controller.orders.impl;

import com.ecommerce.controller.AbstractController;
import com.ecommerce.controller.orders.OrderController;
import com.ecommerce.dto.ApiResponseDTO;
import com.ecommerce.dto.orders.request.CreateOrderRequestDTO;
import com.ecommerce.dto.orders.request.UpdateOrderStatusRequestDTO;
import com.ecommerce.dto.orders.response.OrderResponse;
import com.ecommerce.service.orders.impl.OrderServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class OrderControllerImpl extends AbstractController implements OrderController {

    @Autowired
    private OrderServiceImpl orderServiceImpl;

    @Override
    public ResponseEntity<ApiResponseDTO<OrderResponse>> getOrderById(Long orderId) {
        log.info("GET /v1/orders/{} - Getting order details", orderId);
        return ok(() -> orderServiceImpl.getOrderById(orderId));
    }

    @Override
    public ResponseEntity<ApiResponseDTO<OrderResponse>> updateOrderStatus(Long orderId, UpdateOrderStatusRequestDTO request) {
        log.info("PATCH /v1/orders/{}/status - Updating order status", orderId);
        return ok(() -> orderServiceImpl.updateOrderStatus(orderId, request));
    }

    @Override
    public ResponseEntity<ApiResponseDTO<OrderResponse>> createOrder(CreateOrderRequestDTO request) {
        log.info("POST /v1/orders - Creating order for user: {}", request.getUserId());
        return created(() -> orderServiceImpl.createOrder(request));
    }

    @Override
    public ResponseEntity<ApiResponseDTO<OrderResponse>> updateOrder(Long orderId, CreateOrderRequestDTO request) {
        log.info("PUT /v1/orders/{} - Updating order", orderId);
        return ok(() -> orderServiceImpl.updateOrder(orderId, request));
    }
}
