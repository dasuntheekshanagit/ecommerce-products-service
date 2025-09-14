package com.ecommerce.service.products;

import com.ecommerce.dto.products.response.CategoryResponse;
import java.util.List;

public interface CategoryService {
    List<CategoryResponse> getAllCategories();
    CategoryResponse getCategoryById(Long id);
    List<CategoryResponse> searchCategoriesByName(String name);
}
