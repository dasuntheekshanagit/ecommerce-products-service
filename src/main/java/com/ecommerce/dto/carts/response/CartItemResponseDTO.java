package com.ecommerce.dto.carts.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CartItemResponseDTO {

    private Long id;
    private Long productId;
    private Integer quantity;
    private BigDecimal price;
    private BigDecimal totalPrice;

}

