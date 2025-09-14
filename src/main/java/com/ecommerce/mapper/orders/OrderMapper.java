package com.ecommerce.mapper.orders;

import com.ecommerce.dto.orders.response.OrderItemResponse;
import com.ecommerce.dto.orders.response.OrderResponse;
import com.ecommerce.dto.orders.response.PaymentResponse;
import com.ecommerce.entity.orders.Order;
import com.ecommerce.entity.orders.OrderItem;
import com.ecommerce.entity.orders.Payment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface OrderMapper {

    OrderMapper INSTANCE = Mappers.getMapper(OrderMapper.class);

    @Mapping(target = "items", source = "items")
    @Mapping(target = "payments", source = "payments")
    OrderResponse toResponse(Order order);

    List<OrderResponse> toResponseList(List<Order> orders);

    OrderItemResponse toOrderItemResponse(OrderItem item);

    PaymentResponse toPaymentResponse(Payment payment);

    List<OrderItemResponse> toOrderItemResponseList(List<OrderItem> items);

    List<PaymentResponse> toPaymentResponseList(List<Payment> payments);
}
