package com.ecommerce.controller;

import com.ecommerce.dto.ApiResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.function.Supplier;

public abstract class AbstractController {

    protected <T> ResponseEntity<ApiResponseDTO<T>> ok(Supplier<T> action) {
        return ResponseEntity.ok(ApiResponseDTO.success(action.get()));
    }

    protected <T> ResponseEntity<ApiResponseDTO<T>> created(Supplier<T> action) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponseDTO.success(action.get(),
                "Resource created successfully"));
    }

    protected ResponseEntity<ApiResponseDTO<Void>> noContent(Runnable action) {
        action.run();
        return ResponseEntity.status(HttpStatus.NO_CONTENT)
                .body(ApiResponseDTO.success(null,
                "Operation completed successfully"));
    }
}
