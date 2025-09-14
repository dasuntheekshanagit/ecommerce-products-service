package com.ecommerce.service.products;

import com.ecommerce.dto.products.response.CategoryResponseDTO;
import java.util.List;

public interface CategoryService {
    List<CategoryResponseDTO> getAllCategories();
    CategoryResponseDTO getCategoryById(Long id);
    List<CategoryResponseDTO> searchCategoriesByName(String name);
}
