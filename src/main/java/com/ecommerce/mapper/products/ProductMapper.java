package com.ecommerce.mapper.products;

import com.ecommerce.dto.products.request.CreateProductRequestDTO;
import com.ecommerce.dto.products.request.UpdateProductRequestDTO;
import com.ecommerce.dto.products.response.ProductResponseDTO;
import com.ecommerce.entity.products.Product;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    ProductMapper INSTANCE = Mappers.getMapper(ProductMapper.class);

    Product toEntity(CreateProductRequestDTO request);

    @Mapping(target = "categoryName", source = "category.name")
    ProductResponseDTO toResponse(Product product);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityFromRequest(@MappingTarget Product product, UpdateProductRequestDTO request);
}
