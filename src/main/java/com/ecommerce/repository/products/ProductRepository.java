package com.ecommerce.repository.products;

import com.ecommerce.entity.products.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    // Find products by category
    Page<Product> findByCategoryId(Long categoryId, Pageable pageable);

    // Find products by name containing (case insensitive)
    Page<Product> findByNameContainingIgnoreCase(String name, Pageable pageable);

    // Find products by price range
    Page<Product> findByPriceBetween(BigDecimal minPrice, BigDecimal maxPrice, Pageable pageable);

    // Find products with stock greater than 0
    Page<Product> findByStockGreaterThan(Integer stock, Pageable pageable);

    // Complex query with multiple filters
    @Query("SELECT p FROM Product p WHERE " +
           "(:name IS NULL OR LOWER(p.name) LIKE LOWER(CONCAT('%', :name, '%'))) AND " +
           "(:categoryId IS NULL OR p.categoryId = :categoryId) AND " +
           "(:minPrice IS NULL OR p.price >= :minPrice) AND " +
           "(:maxPrice IS NULL OR p.price <= :maxPrice) AND " +
           "(:inStock IS NULL OR (:inStock = true AND p.stock > 0) OR (:inStock = false))")
    Page<Product> findProductsWithFilters(
            @Param("name") String name,
            @Param("categoryId") Long categoryId,
            @Param("minPrice") BigDecimal minPrice,
            @Param("maxPrice") BigDecimal maxPrice,
            @Param("inStock") Boolean inStock,
            Pageable pageable
    );

    // Find products with category information
    @Query("SELECT p FROM Product p LEFT JOIN FETCH p.category WHERE p.id = :id")
    Product findByIdWithCategory(@Param("id") Long id);

    // Find all products with category information
    @Query("SELECT p FROM Product p LEFT JOIN FETCH p.category")
    List<Product> findAllWithCategory();

    // Count products by category
    long countByCategoryId(Long categoryId);

    // Check if product exists by name (for uniqueness validation)
    boolean existsByNameIgnoreCase(String name);
}

