package com.ecommerce.controller.carts;

import com.ecommerce.dto.ApiResponseDTO;
import com.ecommerce.dto.carts.request.AddCartItemRequestDTO;
import com.ecommerce.dto.carts.request.UpdateCartItemRequestDTO;
import com.ecommerce.dto.carts.response.CartItemResponseDTO;
import com.ecommerce.dto.carts.response.CartResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/v1/carts")
@Tag(name = "Cart", description = "Shopping cart management operations")
@CrossOrigin(origins = "*")
public interface CartController {

    @GetMapping("/{userId}")
    @Operation(summary = "Get cart", description = "Get the current user's shopping cart")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cart retrieved successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid user ID")
    })
    ResponseEntity<ApiResponseDTO<CartResponseDTO>> getCart(
            @Parameter(description = "User ID", required = true)
            @PathVariable Long userId);

    @PostMapping("/{userId}")
    @Operation(summary = "Create cart", description = "Create a cart for the user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Cart create successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request data")
    })
    ResponseEntity<ApiResponseDTO<CartResponseDTO>> createCart(
            @Parameter(description = "User ID", required = true)
            @PathVariable Long userId);

    @PostMapping("/{userId}/items")
    @Operation(summary = "Add item to cart", description = "Add a product to the user's shopping cart")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Item added to cart successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request data")
    })
    ResponseEntity<ApiResponseDTO<CartItemResponseDTO>> addItemToCart(
            @Parameter(description = "User ID", required = true)
            @PathVariable Long userId,
            @Parameter(description = "Item to add to cart")
            @Valid @RequestBody AddCartItemRequestDTO request);

    @PatchMapping("/{userId}/items/{itemId}")
    @Operation(summary = "Update cart item", description = "Update the quantity of an item in the cart")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cart item updated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request data"),
            @ApiResponse(responseCode = "404", description = "Cart item not found")
    })
    ResponseEntity<ApiResponseDTO<CartItemResponseDTO>> updateCartItem(
            @Parameter(description = "User ID", required = true)
            @PathVariable Long userId,
            @Parameter(description = "Cart item ID")
            @PathVariable Long itemId,
            @Parameter(description = "Updated item data")
            @Valid @RequestBody UpdateCartItemRequestDTO request);

    @DeleteMapping("/{userId}/items/{itemId}")
    @Operation(summary = "Remove item from cart", description = "Remove a specific item from the cart")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Item removed from cart successfully"),
            @ApiResponse(responseCode = "404", description = "Cart item not found")
    })
    ResponseEntity<ApiResponseDTO<Void>> removeItemFromCart(
            @Parameter(description = "User ID", required = true)
            @PathVariable Long userId,
            @Parameter(description = "Cart item ID")
            @PathVariable Long itemId);

    @DeleteMapping("/{userId}")
    @Operation(summary = "Clear cart", description = "Remove all items from the user's cart")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Cart cleared successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid user ID")
    })
    ResponseEntity<ApiResponseDTO<Void>> clearCart(
            @Parameter(description = "User ID", required = true)
            @PathVariable Long userId);

    @DeleteMapping("/delete/{userId}")
    @Operation(summary = "Delete cart", description = "Delete the user's cart entirely")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Cart deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Cart not found")
    })
    ResponseEntity<ApiResponseDTO<Void>> deleteCart(
            @Parameter(description = "User ID", required = true)
            @PathVariable Long userId);
}
