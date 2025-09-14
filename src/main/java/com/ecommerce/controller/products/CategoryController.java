package com.ecommerce.controller.products;

import com.ecommerce.dto.ApiResponseDTO;
import com.ecommerce.dto.products.response.CategoryResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/v1/categories")
@Tag(name = "Categories", description = "Category management operations")
@CrossOrigin(origins = "*")
public interface CategoryController {

    @GetMapping
    @Operation(summary = "List categories", description = "Get all available product categories")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Categories retrieved successfully")
    })
    ResponseEntity<ApiResponseDTO<List<CategoryResponse>>> getAllCategories();

    @GetMapping("/{categoryId}")
    @Operation(summary = "Get category details", description = "Get detailed information about a specific category")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Category found"),
            @ApiResponse(responseCode = "404", description = "Category not found")
    })
    ResponseEntity<ApiResponseDTO<CategoryResponse>> getCategoryById(
            @Parameter(description = "Category ID") @PathVariable Long categoryId);

    @GetMapping("/search")
    @Operation(summary = "Search categories", description = "Search categories by name")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Categories found")
    })
    ResponseEntity<ApiResponseDTO<List<CategoryResponse>>> searchCategories(
            @Parameter(description = "Category name to search") @RequestParam String name);
}
