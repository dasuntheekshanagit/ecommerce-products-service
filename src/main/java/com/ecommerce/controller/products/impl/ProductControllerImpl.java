package com.ecommerce.controller.products.impl;

import com.ecommerce.controller.AbstractController;
import com.ecommerce.controller.products.ProductController;
import com.ecommerce.dto.ApiResponseDTO;
import com.ecommerce.dto.products.request.CreateProductRequest;
import com.ecommerce.dto.products.request.UpdateProductRequest;
import com.ecommerce.dto.products.response.PagedResponse;
import com.ecommerce.dto.products.response.ProductResponse;
import com.ecommerce.service.products.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@Slf4j
@RestController
public class ProductControllerImpl extends AbstractController implements ProductController {

    @Autowired
    ProductService productService;

    @Override
    public ResponseEntity<ApiResponseDTO<PagedResponse<ProductResponse>>> getAllProducts(int page, int size, String sortBy, String sortDir, String name, Long categoryId, BigDecimal minPrice, BigDecimal maxPrice, Boolean inStock) {
        log.info("GET /v1/products - page: {}, size: {}, filters: name={}, categoryId={}", page, size, name, categoryId);
        return ok(() -> productService.getAllProducts(page, size, sortBy, sortDir, name, categoryId, minPrice, maxPrice, inStock));
    }

    @Override
    public ResponseEntity<ApiResponseDTO<ProductResponse>> getProductById(Long productId) {
        log.info("GET /v1/products/{}", productId);
        return ok(() -> productService.getProductById(productId));
    }

    @Override
    public ResponseEntity<ApiResponseDTO<ProductResponse>> createProduct(CreateProductRequest request) {
        log.info("POST /v1/products - Creating product: {}", request.getName());
        return created(() -> productService.createProduct(request));
    }

    @Override
    public ResponseEntity<ApiResponseDTO<ProductResponse>> updateProduct(Long productId, UpdateProductRequest request) {
        log.info("PATCH /v1/products/{} - Updating product", productId);
        return ok(() -> productService.updateProduct(productId, request));
    }

    @Override
    public ResponseEntity<ApiResponseDTO<Void>> deleteProduct(Long productId) {
        log.info("DELETE /v1/products/{}", productId);
        productService.deleteProduct(productId);
        return noContent(() -> productService.deleteProduct(productId));
    }
}

