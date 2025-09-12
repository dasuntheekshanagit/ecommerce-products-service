package com.ecommerce.controller.products;

import com.ecommerce.dto.products.response.CategoryResponse;
import com.ecommerce.service.products.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/categories")
@Tag(name = "Categories", description = "Category management operations")
@CrossOrigin(origins = "*")
public class CategoryController {

    private static final Logger logger = LoggerFactory.getLogger(CategoryController.class);

    private final CategoryService categoryService;

    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping
    @Operation(summary = "List categories", description = "Get all available product categories")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Categories retrieved successfully")
    })
    public ResponseEntity<List<CategoryResponse>> getAllCategories() {
        logger.info("GET /v1/categories - Fetching all categories");

        List<CategoryResponse> response = categoryService.getAllCategories();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{categoryId}")
    @Operation(summary = "Get category details", description = "Get detailed information about a specific category")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Category found"),
            @ApiResponse(responseCode = "404", description = "Category not found")
    })
    public ResponseEntity<CategoryResponse> getCategoryById(
            @Parameter(description = "Category ID") @PathVariable Long categoryId) {

        logger.info("GET /v1/categories/{}", categoryId);

        CategoryResponse response = categoryService.getCategoryById(categoryId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/search")
    @Operation(summary = "Search categories", description = "Search categories by name")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Categories found")
    })
    public ResponseEntity<List<CategoryResponse>> searchCategories(
            @Parameter(description = "Category name to search") @RequestParam String name) {

        logger.info("GET /v1/categories/search - Searching categories with name: {}", name);

        List<CategoryResponse> response = categoryService.searchCategoriesByName(name);
        return ResponseEntity.ok(response);
    }
}

