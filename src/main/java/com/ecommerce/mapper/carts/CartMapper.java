package com.ecommerce.mapper.carts;

import com.ecommerce.dto.carts.response.CartItemResponseDTO;
import com.ecommerce.dto.carts.response.CartResponseDTO;
import com.ecommerce.entity.carts.Cart;
import com.ecommerce.entity.carts.CartItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.math.BigDecimal;
import java.util.List;

@Mapper(componentModel = "spring")
public interface CartMapper {

    CartMapper INSTANCE = Mappers.getMapper(CartMapper.class);

    @Mapping(target = "totalPrice", expression = "java(com.ecommerce.mapper.carts.CartMapperUtil.calculateTotalPrice(cart.getItems()))")
    CartResponseDTO toResponse(Cart cart);

    CartItemResponseDTO toItemResponse(CartItem savedItem);
}
