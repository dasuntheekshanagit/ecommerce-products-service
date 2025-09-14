package com.ecommerce.dto.orders.request;

import com.ecommerce.entity.orders.OrderStatus;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateOrderStatusRequestDTO {

    @NotNull(message = "Order status is required")
    private OrderStatus status;
}

