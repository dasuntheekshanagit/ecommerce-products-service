package com.ecommerce.repository.products;

import com.ecommerce.entity.products.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    // Find category by name (case insensitive)
    Optional<Category> findByNameIgnoreCase(String name);

    // Find categories by name containing (case insensitive)
    List<Category> findByNameContainingIgnoreCase(String name);

    // Check if category exists by name (for uniqueness validation)
    boolean existsByNameIgnoreCase(String name);

    // Find category with products count
    @Query("SELECT c FROM Category c LEFT JOIN c.products p WHERE c.id = :id")
    Optional<Category> findByIdWithProducts(@Param("id") Long id);

    // Find all categories ordered by name
    List<Category> findAllByOrderByNameAsc();
}

