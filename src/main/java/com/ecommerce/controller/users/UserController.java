package com.ecommerce.controller.users;

import com.ecommerce.dto.users.request.CreateAddressRequest;
import com.ecommerce.dto.users.request.UpdateUserRequest;
import com.ecommerce.dto.users.response.AddressResponse;
import com.ecommerce.dto.users.response.UserResponse;
import com.ecommerce.service.users.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/users")
@Tag(name = "Users", description = "User profile and address management operations")
@CrossOrigin(origins = "*")
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{userId}")
    @Operation(summary = "Get user profile", description = "Get user profile information including addresses")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User profile retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    public ResponseEntity<UserResponse> getUserProfile(
            @Parameter(description = "User ID") 
            @PathVariable Long userId) {

        logger.info("GET /v1/users/{} - Getting user profile", userId);

        UserResponse response = userService.getUserById(userId);
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{userId}")
    @Operation(summary = "Update user profile", description = "Update user profile information")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User profile updated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request data"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    public ResponseEntity<UserResponse> updateUserProfile(
            @Parameter(description = "User ID") 
            @PathVariable Long userId,
            @Parameter(description = "Updated user data") 
            @Valid @RequestBody UpdateUserRequest request) {

        logger.info("PATCH /v1/users/{} - Updating user profile", userId);

        UserResponse response = userService.updateUser(userId, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{userId}")
    @Operation(summary = "Delete user", description = "Delete user account and all associated data")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "User deleted successfully"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    public ResponseEntity<Void> deleteUser(
            @Parameter(description = "User ID") 
            @PathVariable Long userId) {

        logger.info("DELETE /v1/users/{} - Deleting user", userId);

        userService.deleteUser(userId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{userId}/addresses")
    @Operation(summary = "List user addresses", description = "Get all addresses for a user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Addresses retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    public ResponseEntity<List<AddressResponse>> getUserAddresses(
            @Parameter(description = "User ID") 
            @PathVariable Long userId) {

        logger.info("GET /v1/users/{}/addresses - Getting user addresses", userId);

        List<AddressResponse> addresses = userService.getUserAddresses(userId);
        return ResponseEntity.ok(addresses);
    }

    @PostMapping("/{userId}/addresses")
    @Operation(summary = "Add address", description = "Add a new address for the user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Address added successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request data"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    public ResponseEntity<AddressResponse> addAddress(
            @Parameter(description = "User ID") 
            @PathVariable Long userId,
            @Parameter(description = "Address data") 
            @Valid @RequestBody CreateAddressRequest request) {

        logger.info("POST /v1/users/{}/addresses - Adding address", userId);

        AddressResponse response = userService.addAddress(userId, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PatchMapping("/{userId}/addresses/{addressId}")
    @Operation(summary = "Update address", description = "Update an existing address")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Address updated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request data"),
            @ApiResponse(responseCode = "404", description = "User or address not found")
    })
    public ResponseEntity<AddressResponse> updateAddress(
            @Parameter(description = "User ID") 
            @PathVariable Long userId,
            @Parameter(description = "Address ID") 
            @PathVariable Long addressId,
            @Parameter(description = "Updated address data") 
            @Valid @RequestBody CreateAddressRequest request) {

        logger.info("PATCH /v1/users/{}/addresses/{} - Updating address", userId, addressId);

        AddressResponse response = userService.updateAddress(userId, addressId, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{userId}/addresses/{addressId}")
    @Operation(summary = "Remove address", description = "Remove an address from the user's profile")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Address removed successfully"),
            @ApiResponse(responseCode = "404", description = "User or address not found")
    })
    public ResponseEntity<Void> removeAddress(
            @Parameter(description = "User ID") 
            @PathVariable Long userId,
            @Parameter(description = "Address ID") 
            @PathVariable Long addressId) {

        logger.info("DELETE /v1/users/{}/addresses/{} - Removing address", userId, addressId);

        userService.deleteAddress(userId, addressId);
        return ResponseEntity.noContent().build();
    }
}

