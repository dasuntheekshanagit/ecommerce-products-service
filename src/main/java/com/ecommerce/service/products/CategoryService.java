package com.ecommerce.service.products;

import com.ecommerce.dto.products.CategoryMapper;
import com.ecommerce.dto.products.response.CategoryResponse;
import com.ecommerce.entity.products.Category;
import com.ecommerce.exception.products.exception.ResourceNotFoundException;
import com.ecommerce.repository.products.CategoryRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class CategoryService {

    private static final Logger logger = LoggerFactory.getLogger(CategoryService.class);

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    @Autowired
    public CategoryService(CategoryRepository categoryRepository, CategoryMapper categoryMapper) {
        this.categoryRepository = categoryRepository;
        this.categoryMapper = categoryMapper;
    }

    @Transactional(readOnly = true)
    public List<CategoryResponse> getAllCategories() {
        logger.info("Fetching all categories");

        List<Category> categories = categoryRepository.findAllByOrderByNameAsc();
        return categories.stream()
                .map(categoryMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public CategoryResponse getCategoryById(Long id) {
        logger.info("Fetching category with id: {}", id);

        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with id: " + id));

        return categoryMapper.toResponse(category);
    }

    @Transactional(readOnly = true)
    public List<CategoryResponse> searchCategoriesByName(String name) {
        logger.info("Searching categories with name containing: {}", name);

        List<Category> categories = categoryRepository.findByNameContainingIgnoreCase(name);
        return categories.stream()
                .map(categoryMapper::toResponse)
                .collect(Collectors.toList());
    }
}

