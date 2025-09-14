package com.ecommerce.service.orders;

import com.ecommerce.dto.orders.OrderMapper;
import com.ecommerce.dto.orders.request.CreateOrderRequest;
import com.ecommerce.dto.orders.request.OrderItemRequest;
import com.ecommerce.dto.orders.request.UpdateOrderStatusRequest;
import com.ecommerce.dto.orders.response.OrderResponse;
import com.ecommerce.entity.orders.Order;
import com.ecommerce.entity.orders.OrderItem;
import com.ecommerce.entity.orders.OrderStatus;
import com.ecommerce.entity.orders.Payment;
import com.ecommerce.exception.ResourceNotFoundException;
import com.ecommerce.repository.orders.OrderRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class OrderService {

    private static final Logger logger = LoggerFactory.getLogger(OrderService.class);

    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;

    @Autowired
    public OrderService(OrderRepository orderRepository, OrderMapper orderMapper) {
        this.orderRepository = orderRepository;
        this.orderMapper = orderMapper;
    }

    @Transactional(readOnly = true)
    public List<OrderResponse> getUserOrders(Long userId) {
        logger.info("Fetching orders for user: {}", userId);

        List<Order> orders = orderRepository.findByUserIdOrderByCreatedAtDesc(userId);
        return orderMapper.toResponseList(orders);
    }

    @Transactional(readOnly = true)
    public Page<OrderResponse> getAllOrders(Pageable pageable) {
        logger.info("Fetching all orders with pagination");

        Page<Order> orders = orderRepository.findAllByOrderByCreatedAtDesc(pageable);
        return orders.map(orderMapper::toResponse);
    }

    public OrderResponse createOrder(CreateOrderRequest request) {
        logger.info("Creating order for user: {}", request.getUserId());

        // Create order
        Order order = new Order(request.getUserId(), OrderStatus.PENDING, request.getTotalPrice());

        // Add order items
        for (OrderItemRequest itemRequest : request.getItems()) {
            OrderItem orderItem = new OrderItem(
                    order,
                    itemRequest.getProductId(),
                    itemRequest.getQuantity(),
                    itemRequest.getPrice()
            );
            order.addItem(orderItem);
        }

        // Add payment if provided
        if (request.getPayment() != null) {
            Payment payment = new Payment(
                    order,
                    request.getPayment().getPaymentMethod(),
                    request.getPayment().getAmount(),
                    "PENDING"
            );
            order.addPayment(payment);
        }

        Order savedOrder = orderRepository.save(order);
        logger.info("Created order with ID: {} for user: {}", savedOrder.getId(), request.getUserId());

        return orderMapper.toResponse(savedOrder);
    }

    @Transactional(readOnly = true)
    public OrderResponse getOrderById(Long orderId) {
        logger.info("Fetching order with ID: {}", orderId);

        Order order = orderRepository.findByIdWithItemsAndPayments(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found with id: " + orderId));

        return orderMapper.toResponse(order);
    }

    @Transactional(readOnly = true)
    public OrderResponse getUserOrderById(Long userId, Long orderId) {
        logger.info("Fetching order ID: {} for user: {}", orderId, userId);

        Order order = orderRepository.findByIdAndUserId(orderId, userId)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found with id: " + orderId + " for user: " + userId));

        // Fetch with items and payments
        order = orderRepository.findByIdWithItemsAndPayments(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found with id: " + orderId));

        return orderMapper.toResponse(order);
    }

    public OrderResponse updateOrderStatus(Long orderId, UpdateOrderStatusRequest request) {
        logger.info("Updating order ID: {} status to: {}", orderId, request.getStatus());

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found with id: " + orderId));

        // Validate status transition
        if (!isValidStatusTransition(order.getStatus(), request.getStatus())) {
            throw new IllegalArgumentException("Invalid status transition from " + order.getStatus() + " to " + request.getStatus());
        }

        order.setStatus(request.getStatus());
        Order updatedOrder = orderRepository.save(order);

        logger.info("Updated order ID: {} status to: {}", orderId, request.getStatus());
        return orderMapper.toResponse(updatedOrder);
    }

    public OrderResponse updateUserOrderStatus(Long userId, Long orderId, UpdateOrderStatusRequest request) {
        logger.info("Updating order ID: {} status to: {} for user: {}", orderId, request.getStatus(), userId);

        Order order = orderRepository.findByIdAndUserId(orderId, userId)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found with id: " + orderId + " for user: " + userId));

        // Users can only cancel orders
        if (request.getStatus() != OrderStatus.CANCELLED) {
            throw new IllegalArgumentException("Users can only cancel orders");
        }

        // Check if order can be cancelled
        if (!canBeCancelled(order.getStatus())) {
            throw new IllegalArgumentException("Order cannot be cancelled in current status: " + order.getStatus());
        }

        order.setStatus(request.getStatus());
        Order updatedOrder = orderRepository.save(order);

        logger.info("Updated order ID: {} status to: {} for user: {}", orderId, request.getStatus(), userId);
        return orderMapper.toResponse(updatedOrder);
    }

    public void deleteOrder(Long userId, Long orderId) {
        logger.info("Deleting order ID: {} for user: {}", orderId, userId);

        Order order = orderRepository.findByIdAndUserId(orderId, userId)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found with id: " + orderId + " for user: " + userId));

        // Only allow deletion of cancelled orders
        if (order.getStatus() != OrderStatus.CANCELLED) {
            throw new IllegalArgumentException("Only cancelled orders can be deleted");
        }

        orderRepository.delete(order);
        logger.info("Deleted order ID: {} for user: {}", orderId, userId);
    }

    private boolean isValidStatusTransition(OrderStatus from, OrderStatus to) {
        // Define valid status transitions
        switch (from) {
            case PENDING:
                return to == OrderStatus.CONFIRMED || to == OrderStatus.CANCELLED;
            case CONFIRMED:
                return to == OrderStatus.PROCESSING || to == OrderStatus.CANCELLED;
            case PROCESSING:
                return to == OrderStatus.SHIPPED || to == OrderStatus.CANCELLED;
            case SHIPPED:
                return to == OrderStatus.DELIVERED;
            case DELIVERED:
                return to == OrderStatus.REFUNDED;
            case CANCELLED:
            case REFUNDED:
                return false; // Terminal states
            default:
                return false;
        }
    }

    private boolean canBeCancelled(OrderStatus status) {
        return status == OrderStatus.PENDING || status == OrderStatus.CONFIRMED;
    }

    public OrderResponse updateOrder(Long orderId, CreateOrderRequest request) {
        return  null;
    }
}

