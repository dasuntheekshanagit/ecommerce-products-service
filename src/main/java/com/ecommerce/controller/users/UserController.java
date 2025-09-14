package com.ecommerce.controller.users;

import com.ecommerce.dto.ApiResponseDTO;
import com.ecommerce.dto.users.request.CreateAddressRequestDTO;
import com.ecommerce.dto.users.request.UpdateUserRequestDTO;
import com.ecommerce.dto.users.response.AddressResponseDTO;
import com.ecommerce.dto.users.response.UserResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/v1/users")
@Tag(name = "Users", description = "User profile and address management operations")
@CrossOrigin(origins = "*")
public interface UserController {

    @GetMapping("/{userId}")
    @Operation(summary = "Get user profile", description = "Get user profile information including addresses")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User profile retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    ResponseEntity<ApiResponseDTO<UserResponseDTO>> getUserProfile(
            @Parameter(description = "User ID")
            @PathVariable Long userId);

    @PostMapping("/{userId}")
    @Operation(summary = "Create user profile", description = "Create a user profile")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "User profile created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request data")
    })
    ResponseEntity<ApiResponseDTO<UserResponseDTO>> createUserProfile(
            @Parameter(description = "User ID")
            @PathVariable Long userId,
            @Parameter(description = "Create user data")
            @Valid @RequestBody UpdateUserRequestDTO request);

    @PatchMapping("/{userId}")
    @Operation(summary = "Update user profile", description = "Update user profile information")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User profile updated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request data"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    ResponseEntity<ApiResponseDTO<UserResponseDTO>> updateUserProfile(
            @Parameter(description = "User ID")
            @PathVariable Long userId,
            @Parameter(description = "Updated user data")
            @Valid @RequestBody UpdateUserRequestDTO request);

    @DeleteMapping("/{userId}")
    @Operation(summary = "Delete user", description = "Delete user account and all associated data")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "User deleted successfully"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    ResponseEntity<Void> deleteUser(
            @Parameter(description = "User ID")
            @PathVariable Long userId);

    @GetMapping("/{userId}/addresses")
    @Operation(summary = "List user addresses", description = "Get all addresses for a user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Addresses retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    ResponseEntity<ApiResponseDTO<List<AddressResponseDTO>>> getUserAddresses(
            @Parameter(description = "User ID") @PathVariable Long userId,
            @Parameter(description = "All Address") @RequestParam(defaultValue = "true") boolean fetchAllAddress);

    @PostMapping("/{userId}/addresses")
    @Operation(summary = "Add address", description = "Add a new address for the user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Address added successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request data"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    ResponseEntity<AddressResponseDTO> addAddress(
            @Parameter(description = "User ID")
            @PathVariable Long userId,
            @Parameter(description = "Address data")
            @Valid @RequestBody CreateAddressRequestDTO request);

    @PatchMapping("/{userId}/addresses/{addressId}")
    @Operation(summary = "Update address", description = "Update an existing address")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Address updated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request data"),
            @ApiResponse(responseCode = "404", description = "User or address not found")
    })
    ResponseEntity<ApiResponseDTO<AddressResponseDTO>> updateAddress(
            @Parameter(description = "User ID")
            @PathVariable Long userId,
            @Parameter(description = "Address ID")
            @PathVariable Long addressId,
            @Parameter(description = "Updated address data")
            @Valid @RequestBody CreateAddressRequestDTO request);

    @DeleteMapping("/{userId}/addresses/{addressId}")
    @Operation(summary = "Remove address", description = "Remove an address from the user's profile")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Address removed successfully"),
            @ApiResponse(responseCode = "404", description = "User or address not found")
    })
    ResponseEntity<Void> removeAddress(
            @Parameter(description = "User ID")
            @PathVariable Long userId,
            @Parameter(description = "Address ID")
            @PathVariable Long addressId);
}

