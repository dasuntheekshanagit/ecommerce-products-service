package com.ecommerce.controller.products;

import com.ecommerce.dto.ApiResponseDTO;
import com.ecommerce.dto.products.request.CreateProductRequest;
import com.ecommerce.dto.products.request.UpdateProductRequest;
import com.ecommerce.dto.products.response.PagedResponse;
import com.ecommerce.dto.products.response.ProductResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RequestMapping("/v1/products")
@Tag(name = "Products", description = "Product management operations")
@CrossOrigin(origins = "*")
public interface ProductController {

    @GetMapping
    @Operation(summary = "List products", description = "Get a paginated list of products with optional filters")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Products retrieved successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request parameters")
    })
    ResponseEntity<ApiResponseDTO<PagedResponse<ProductResponse>>> getAllProducts(
            @Parameter(description = "Page number (0-based)") @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Page size") @RequestParam(defaultValue = "20") int size,
            @Parameter(description = "Sort field") @RequestParam(required = false) String sortBy,
            @Parameter(description = "Sort direction (asc/desc)") @RequestParam(required = false) String sortDir,
            @Parameter(description = "Filter by product name") @RequestParam(required = false) String name,
            @Parameter(description = "Filter by category ID") @RequestParam(required = false) Long categoryId,
            @Parameter(description = "Minimum price filter") @RequestParam(required = false) BigDecimal minPrice,
            @Parameter(description = "Maximum price filter") @RequestParam(required = false) BigDecimal maxPrice,
            @Parameter(description = "Filter by stock availability") @RequestParam(required = false) Boolean inStock);

    @GetMapping("/{productId}")
    @Operation(summary = "Get product details", description = "Get detailed information about a specific product")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Product found"),
            @ApiResponse(responseCode = "404", description = "Product not found")
    })
    ResponseEntity<ApiResponseDTO<ProductResponse>> getProductById(
            @Parameter(description = "Product ID") @PathVariable Long productId);

    @PostMapping
    @Operation(summary = "Create product", description = "Create a new product (admin only)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Product created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid product data"),
            @ApiResponse(responseCode = "404", description = "Category not found")
    })
    ResponseEntity<ApiResponseDTO<ProductResponse>> createProduct(
            @Parameter(description = "Product creation data") @Valid @RequestBody CreateProductRequest request);

    @PatchMapping("/{productId}")
    @Operation(summary = "Update product", description = "Update an existing product")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Product updated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid product data"),
            @ApiResponse(responseCode = "404", description = "Product or category not found")
    })
    ResponseEntity<ApiResponseDTO<ProductResponse>> updateProduct(
            @Parameter(description = "Product ID") @PathVariable Long productId,
            @Parameter(description = "Product update data") @Valid @RequestBody UpdateProductRequest request);

    @DeleteMapping("/{productId}")
    @Operation(summary = "Delete product", description = "Delete an existing product")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Product deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Product not found")
    })
    ResponseEntity<ApiResponseDTO<Void>> deleteProduct(
            @Parameter(description = "Product ID") @PathVariable Long productId);
}
