package com.ecommerce.dto.orders;

import com.ecommerce.dto.orders.response.OrderItemResponse;
import com.ecommerce.dto.orders.response.OrderResponse;
import com.ecommerce.dto.orders.response.PaymentResponse;
import com.ecommerce.entity.orders.Order;
import com.ecommerce.entity.orders.OrderItem;
import com.ecommerce.entity.orders.Payment;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class OrderMapper {

    public OrderResponse toResponse(Order order) {
        if (order == null) {
            return null;
        }

        List<OrderItemResponse> itemResponses = order.getItems()
                .stream()
                .map(this::toOrderItemResponse)
                .collect(Collectors.toList());

        List<PaymentResponse> paymentResponses = order.getPayments()
                .stream()
                .map(this::toPaymentResponse)
                .collect(Collectors.toList());

        return new OrderResponse(
                order.getId(),
                order.getUserId(),
//                order.getStatus(),
                order.getTotalPrice(),
                order.getTotalItems(),
                itemResponses,
                paymentResponses,
                order.getCreatedAt(),
                order.getUpdatedAt()
        );
    }

    public OrderItemResponse toOrderItemResponse(OrderItem item) {
        if (item == null) {
            return null;
        }

        return new OrderItemResponse(
                item.getId(),
                item.getOrder() != null ? item.getOrder().getId() : null,
                item.getProductId(),
                item.getQuantity(),
                item.getPrice(),
                item.getTotalPrice()
        );
    }

    public PaymentResponse toPaymentResponse(Payment payment) {
        if (payment == null) {
            return null;
        }

        return new PaymentResponse(
                payment.getId(),
                payment.getOrder() != null ? payment.getOrder().getId() : null,
                payment.getPaymentMethod(),
                payment.getAmount(),
                payment.getStatus(),
                payment.getCreatedAt()
        );
    }

    public List<OrderResponse> toResponseList(List<Order> orders) {
        if (orders == null) {
            return null;
        }

        return orders.stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }
}

