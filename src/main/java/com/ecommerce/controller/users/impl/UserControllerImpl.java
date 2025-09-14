package com.ecommerce.controller.users.impl;

import com.ecommerce.controller.AbstractController;
import com.ecommerce.controller.users.UserController;
import com.ecommerce.dto.ApiResponseDTO;
import com.ecommerce.dto.users.request.CreateAddressRequestDTO;
import com.ecommerce.dto.users.request.UpdateUserRequestDTO;
import com.ecommerce.dto.users.response.AddressResponseDTO;
import com.ecommerce.dto.users.response.UserResponseDTO;
import com.ecommerce.service.users.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
public class UserControllerImpl extends AbstractController implements UserController {

    @Autowired
    UserService userService;

    public UserControllerImpl(UserService userService) {
        this.userService = userService;
    }

    @Override
    public ResponseEntity<ApiResponseDTO<UserResponseDTO>> getUserProfile(Long userId) {
        log.info("GET /v1/users/{} - Getting user profile", userId);
        return ok(() -> userService.getUserById(userId));
    }

    // TODO:
    @Override
    public ResponseEntity<ApiResponseDTO<UserResponseDTO>> createUserProfile( UpdateUserRequestDTO request) {
        return null;
    }

    @Override
    public ResponseEntity<ApiResponseDTO<UserResponseDTO>> updateUserProfile(Long userId, UpdateUserRequestDTO request) {
        log.info("PATCH /v1/users/{} - Updating user profile", userId);
        return ok(() -> userService.updateUser(userId, request));
    }

    @Override
    public ResponseEntity<Void> deleteUser(Long userId) {
        log.info("DELETE /v1/users/{} - Deleting user", userId);
        userService.deleteUser(userId);
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<ApiResponseDTO<List<AddressResponseDTO>>> getUserAddresses(Long userId, boolean fetchAllAddress) {
        log.info("GET /v1/users/{}/addresses - Getting user addresses", userId);
        return ok(() -> userService.getUserAddresses(userId));
    }

    @Override
    public ResponseEntity<AddressResponseDTO> addAddress(Long userId, CreateAddressRequestDTO request) {
        log.info("POST /v1/users/{}/addresses - Adding address", userId);
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.addAddress(userId, request));
    }

    @Override
    public ResponseEntity<ApiResponseDTO<AddressResponseDTO>> updateAddress(Long userId, Long addressId, CreateAddressRequestDTO request) {
        log.info("PATCH /v1/users/{}/addresses/{} - Updating address", userId, addressId);
        return ok(() -> userService.updateAddress(userId, addressId, request));
    }

    @Override
    public ResponseEntity<Void> removeAddress(Long userId, Long addressId) {
        log.info("DELETE /v1/users/{}/addresses/{} - Removing address", userId, addressId);
        userService.deleteAddress(userId, addressId);
        return ResponseEntity.noContent().build();
    }
}

