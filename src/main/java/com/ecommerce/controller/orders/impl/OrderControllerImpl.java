package com.ecommerce.controller.orders.impl;

import com.ecommerce.controller.AbstractController;
import com.ecommerce.controller.orders.OrderController;
import com.ecommerce.dto.ApiResponseDTO;
import com.ecommerce.dto.orders.request.CreateOrderRequest;
import com.ecommerce.dto.orders.request.UpdateOrderStatusRequest;
import com.ecommerce.dto.orders.response.OrderResponse;
import com.ecommerce.service.orders.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class OrderControllerImpl extends AbstractController implements OrderController {

    @Autowired
    private OrderService orderService;

    @Override
    public ResponseEntity<ApiResponseDTO<OrderResponse>> getOrderById(Long orderId) {
        log.info("GET /v1/admin/orders/{} - Getting order details (admin)", orderId);
        return ok(() -> orderService.getOrderById(orderId));
    }

    @Override
    public ResponseEntity<ApiResponseDTO<OrderResponse>> updateOrderStatus(Long orderId, UpdateOrderStatusRequest request) {
        log.info("PATCH /v1/admin/orders/{}/status - Updating order status (admin)", orderId);
        return ok(() -> orderService.updateOrderStatus(orderId, request));
    }

    @Override
    public ResponseEntity<ApiResponseDTO<OrderResponse>> createOrder(CreateOrderRequest request) {
        log.info("POST /v1/orders - Creating order for user: {}", request.getUserId());
        return created(() -> orderService.createOrder(request));
    }

    @Override
    public ResponseEntity<ApiResponseDTO<OrderResponse>> updateOrder(Long orderId, CreateOrderRequest request) {
        log.info("PUT /v1/orders/{} - Updating order", orderId);
        return ok(() -> orderService.updateOrder(orderId, request));
    }
}
