package com.ecommerce.controller.products;

import com.ecommerce.dto.products.request.CreateProductRequest;
import com.ecommerce.dto.products.request.UpdateProductRequest;
import com.ecommerce.dto.products.response.PagedResponse;
import com.ecommerce.dto.products.response.ProductResponse;
import com.ecommerce.service.products.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/v1/products")
@Tag(name = "Products", description = "Product management operations")
@CrossOrigin(origins = "*")
public class ProductController {

    private static final Logger logger = LoggerFactory.getLogger(ProductController.class);

    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    @Operation(summary = "List products", description = "Get a paginated list of products with optional filters")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Products retrieved successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request parameters")
    })
    public ResponseEntity<PagedResponse<ProductResponse>> getAllProducts(
            @Parameter(description = "Page number (0-based)") @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Page size") @RequestParam(defaultValue = "20") int size,
            @Parameter(description = "Sort field") @RequestParam(required = false) String sortBy,
            @Parameter(description = "Sort direction (asc/desc)") @RequestParam(required = false) String sortDir,
            @Parameter(description = "Filter by product name") @RequestParam(required = false) String name,
            @Parameter(description = "Filter by category ID") @RequestParam(required = false) Long categoryId,
            @Parameter(description = "Minimum price filter") @RequestParam(required = false) BigDecimal minPrice,
            @Parameter(description = "Maximum price filter") @RequestParam(required = false) BigDecimal maxPrice,
            @Parameter(description = "Filter by stock availability") @RequestParam(required = false) Boolean inStock) {

        logger.info("GET /v1/products - page: {}, size: {}, filters: name={}, categoryId={}", 
                   page, size, name, categoryId);

        PagedResponse<ProductResponse> response = productService.getAllProducts(
                page, size, sortBy, sortDir, name, categoryId, minPrice, maxPrice, inStock);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{productId}")
    @Operation(summary = "Get product details", description = "Get detailed information about a specific product")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Product found"),
            @ApiResponse(responseCode = "404", description = "Product not found")
    })
    public ResponseEntity<ProductResponse> getProductById(
            @Parameter(description = "Product ID") @PathVariable Long productId) {

        logger.info("GET /v1/products/{}", productId);

        ProductResponse response = productService.getProductById(productId);
        return ResponseEntity.ok(response);
    }

    @PostMapping
    @Operation(summary = "Create product", description = "Create a new product (admin only)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Product created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid product data"),
            @ApiResponse(responseCode = "404", description = "Category not found")
    })
    public ResponseEntity<ProductResponse> createProduct(
            @Parameter(description = "Product creation data") @Valid @RequestBody CreateProductRequest request) {

        logger.info("POST /v1/products - Creating product: {}", request.getName());

        ProductResponse response = productService.createProduct(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PatchMapping("/{productId}")
    @Operation(summary = "Update product", description = "Update an existing product")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Product updated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid product data"),
            @ApiResponse(responseCode = "404", description = "Product or category not found")
    })
    public ResponseEntity<ProductResponse> updateProduct(
            @Parameter(description = "Product ID") @PathVariable Long productId,
            @Parameter(description = "Product update data") @Valid @RequestBody UpdateProductRequest request) {

        logger.info("PATCH /v1/products/{} - Updating product", productId);

        ProductResponse response = productService.updateProduct(productId, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{productId}")
    @Operation(summary = "Delete product", description = "Delete an existing product")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Product deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Product not found")
    })
    public ResponseEntity<Void> deleteProduct(
            @Parameter(description = "Product ID") @PathVariable Long productId) {

        logger.info("DELETE /v1/products/{}", productId);

        productService.deleteProduct(productId);
        return ResponseEntity.noContent().build();
    }
}

