package com.ecommerce.service.products;

import com.ecommerce.dto.products.request.CreateProductRequest;
import com.ecommerce.dto.products.request.UpdateProductRequest;
import com.ecommerce.dto.products.response.PagedResponse;
import com.ecommerce.dto.products.response.ProductResponse;

import java.math.BigDecimal;

public interface ProductService {
    PagedResponse<ProductResponse> getAllProducts(int page, int size, String sortBy, String sortDir,
                                                 String name, Long categoryId, BigDecimal minPrice,
                                                 BigDecimal maxPrice, Boolean inStock);
    ProductResponse getProductById(Long id);
    ProductResponse createProduct(CreateProductRequest request);
    ProductResponse updateProduct(Long id, UpdateProductRequest request);
    void deleteProduct(Long id);
    PagedResponse<ProductResponse> getProductsByCategory(Long categoryId, int page, int size);
    long getProductCountByCategory(Long categoryId);
}
