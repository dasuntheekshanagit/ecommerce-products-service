package com.ecommerce.service.products.impl;

import com.ecommerce.mapper.products.ProductMapper;
import com.ecommerce.dto.products.request.CreateProductRequestDTO;
import com.ecommerce.dto.products.request.UpdateProductRequestDTO;
import com.ecommerce.dto.products.response.PagedResponseDTO;
import com.ecommerce.dto.products.response.ProductResponseDTO;
import com.ecommerce.entity.products.Product;
import com.ecommerce.exception.ResourceNotFoundException;
import com.ecommerce.repository.products.CategoryRepository;
import com.ecommerce.repository.products.ProductRepository;
import com.ecommerce.service.products.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional
public class ProductServiceImpl implements ProductService {

    @Autowired
    ProductRepository productRepository;

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    ProductMapper productMapper;

    @Override
    @Transactional(readOnly = true)
    public PagedResponseDTO<ProductResponseDTO> getAllProducts(int page, int size, String sortBy, String sortDir,
                                                               String name, Long categoryId, BigDecimal minPrice,
                                                               BigDecimal maxPrice, Boolean inStock) {
        log.info("Fetching products with filters - page: {}, size: {}, name: {}, categoryId: {}",
                page, size, name, categoryId);
        sortBy = (sortBy != null && !sortBy.isEmpty()) ? sortBy : "id";
        sortDir = (sortDir != null && sortDir.equalsIgnoreCase("desc")) ? "desc" : "asc";
        Sort sort = sortDir.equalsIgnoreCase("desc") ?
                Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<Product> productPage = productRepository.findProductsWithFilters(
                name, categoryId, minPrice, maxPrice, inStock, pageable);
        List<ProductResponseDTO> productResponses = productPage.getContent()
                .stream()
                .map(productMapper::toResponse)
                .collect(Collectors.toList());
        return new PagedResponseDTO<>(
                productResponses,
                productPage.getNumber(),
                productPage.getSize(),
                productPage.getTotalElements(),
                productPage.getTotalPages(),
                productPage.isFirst(),
                productPage.isLast(),
                productPage.isEmpty()
        );
    }

    @Override
    @Transactional(readOnly = true)
    public ProductResponseDTO getProductById(Long id) {
        log.info("Fetching product with id: {}", id);
        Product product = productRepository.findByIdWithCategory(id);
        if (product == null) {
            throw new ResourceNotFoundException("Product not found with id: " + id);
        }
        return productMapper.toResponse(product);
    }

    @Override
    public ProductResponseDTO createProduct(CreateProductRequestDTO request) {
        log.info("Creating new product: {}", request.getName());
        if (!categoryRepository.existsById(request.getCategoryId())) {
            throw new ResourceNotFoundException("Category not found with id: " + request.getCategoryId());
        }
        Product product = productMapper.toEntity(request);
        Product savedProduct = productRepository.save(product);
        log.info("Product created successfully with id: {}", savedProduct.getId());
        return productMapper.toResponse(savedProduct);
    }

    @Override
    public ProductResponseDTO updateProduct(Long id, UpdateProductRequestDTO request) {
        log.info("Updating product with id: {}", id);
        Product existingProduct = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + id));
        if (request.getCategoryId() != null &&
                !categoryRepository.existsById(request.getCategoryId())) {
            throw new ResourceNotFoundException("Category not found with id: " + request.getCategoryId());
        }
        productMapper.updateEntityFromRequest(existingProduct, request);
        Product updatedProduct = productRepository.save(existingProduct);
        log.info("Product updated successfully with id: {}", updatedProduct.getId());
        return productMapper.toResponse(updatedProduct);
    }

    @Override
    public void deleteProduct(Long id) {
        log.info("Deleting product with id: {}", id);
        if (!productRepository.existsById(id)) {
            throw new ResourceNotFoundException("Product not found with id: " + id);
        }
        productRepository.deleteById(id);
        log.info("Product deleted successfully with id: {}", id);
    }

    @Override
    @Transactional(readOnly = true)
    public PagedResponseDTO<ProductResponseDTO> getProductsByCategory(Long categoryId, int page, int size) {
        log.info("Fetching products for category id: {}", categoryId);
        if (!categoryRepository.existsById(categoryId)) {
            throw new ResourceNotFoundException("Category not found with id: " + categoryId);
        }
        Pageable pageable = PageRequest.of(page, size, Sort.by("name").ascending());
        Page<Product> productPage = productRepository.findByCategoryId(categoryId, pageable);
        List<ProductResponseDTO> productResponsDTOS = productPage.getContent()
                .stream()
                .map(productMapper::toResponse)
                .collect(Collectors.toList());
        return new PagedResponseDTO<>(
                productResponsDTOS,
                productPage.getNumber(),
                productPage.getSize(),
                productPage.getTotalElements(),
                productPage.getTotalPages(),
                productPage.isFirst(),
                productPage.isLast(),
                productPage.isEmpty()
        );
    }

    @Override
    @Transactional(readOnly = true)
    public long getProductCountByCategory(Long categoryId) {
        return productRepository.countByCategoryId(categoryId);
    }
}

