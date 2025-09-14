package com.ecommerce.mapper.products;

import com.ecommerce.dto.products.response.CategoryResponseDTO;
import com.ecommerce.entity.products.Category;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface CategoryMapper {

    CategoryMapper INSTANCE = Mappers.getMapper(CategoryMapper.class);

    CategoryResponseDTO toResponse(Category category);
}
