package com.ecommerce.controller.products.impl;

import com.ecommerce.controller.AbstractController;
import com.ecommerce.controller.products.CategoryController;
import com.ecommerce.dto.ApiResponseDTO;
import com.ecommerce.dto.products.response.CategoryResponseDTO;
import com.ecommerce.service.products.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
public class CategoryControllerImpl extends AbstractController implements CategoryController {

    @Autowired
    CategoryService categoryService;

    @Override
    public ResponseEntity<ApiResponseDTO<List<CategoryResponseDTO>>> getAllCategories() {
        log.info("GET /v1/categories - Fetching all categories");
        return ok(() -> categoryService.getAllCategories());
    }

    @Override
    public ResponseEntity<ApiResponseDTO<CategoryResponseDTO>> getCategoryById(Long categoryId) {
        log.info("GET /v1/categories/{}", categoryId);
        return ok(() -> categoryService.getCategoryById(categoryId));
    }

    @Override
    public ResponseEntity<ApiResponseDTO<List<CategoryResponseDTO>>> searchCategories(String name) {
        log.info("GET /v1/categories/search - Searching categories with name: {}", name);
        return ok(() -> categoryService.searchCategoriesByName(name));
    }
}

