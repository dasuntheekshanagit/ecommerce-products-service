package com.ecommerce.service.products;

import com.ecommerce.dto.products.request.CreateProductRequestDTO;
import com.ecommerce.dto.products.request.UpdateProductRequestDTO;
import com.ecommerce.dto.products.response.PagedResponseDTO;
import com.ecommerce.dto.products.response.ProductResponseDTO;

import java.math.BigDecimal;

public interface ProductService {
    PagedResponseDTO<ProductResponseDTO> getAllProducts(int page, int size, String sortBy, String sortDir,
                                                        String name, Long categoryId, BigDecimal minPrice,
                                                        BigDecimal maxPrice, Boolean inStock);
    ProductResponseDTO getProductById(Long id);
    ProductResponseDTO createProduct(CreateProductRequestDTO request);
    ProductResponseDTO updateProduct(Long id, UpdateProductRequestDTO request);
    void deleteProduct(Long id);
    PagedResponseDTO<ProductResponseDTO> getProductsByCategory(Long categoryId, int page, int size);
    long getProductCountByCategory(Long categoryId);
}
