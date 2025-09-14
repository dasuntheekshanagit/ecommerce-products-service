package com.ecommerce.controller.orders;

import com.ecommerce.dto.ApiResponseDTO;
import com.ecommerce.dto.orders.request.CreateOrderRequestDTO;
import com.ecommerce.dto.orders.request.UpdateOrderStatusRequestDTO;
import com.ecommerce.dto.orders.response.OrderResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/v1/orders")
@Tag(name = "Order", description = "Order management operations")
@CrossOrigin(origins = "*")
public interface OrderController {

    @GetMapping("/{orderId}")
    @Operation(summary = "Get order details", description = "Get order details (admin only)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Order retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Order not found")
    })
    ResponseEntity<ApiResponseDTO<OrderResponse>> getOrderById(
            @Parameter(description = "Order ID")
            @PathVariable Long orderId);

    @PatchMapping("/{orderId}/status")
    @Operation(summary = "Update order status", description = "Update order status (admin only)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Order status updated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid status transition"),
            @ApiResponse(responseCode = "404", description = "Order not found")
    })
    ResponseEntity<ApiResponseDTO<OrderResponse>> updateOrderStatus(
            @Parameter(description = "Order ID")
            @PathVariable Long orderId,
            @Parameter(description = "Order status update")
            @Valid @RequestBody UpdateOrderStatusRequestDTO request);

    @PostMapping("")
    @Operation(summary = "Create order", description = "Create a new order")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Order created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request data")
    })
    ResponseEntity<ApiResponseDTO<OrderResponse>> createOrder(
            @Parameter(description = "Order create request")
            @Valid @RequestBody CreateOrderRequestDTO request);

    @PutMapping("/{orderId}")
    @Operation(summary = "Update order", description = "Update an existing order")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Order updated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request data"),
            @ApiResponse(responseCode = "404", description = "Order not found")
    })
    ResponseEntity<ApiResponseDTO<OrderResponse>> updateOrder(
            @Parameter(description = "Order ID")
            @PathVariable Long orderId,
            @Parameter(description = "Order update request")
            @Valid @RequestBody CreateOrderRequestDTO request);
}
