package com.ecommerce.service.users;

import com.ecommerce.dto.users.request.CreateAddressRequest;
import com.ecommerce.dto.users.request.UpdateUserRequest;
import com.ecommerce.dto.users.response.AddressResponse;
import com.ecommerce.dto.users.response.UserResponse;

import java.util.List;

public interface UserService {
    UserResponse getUserById(Long userId);
    UserResponse updateUser(Long userId, UpdateUserRequest request);
    void deleteUser(Long userId);
    List<AddressResponse> getUserAddresses(Long userId);
    AddressResponse addAddress(Long userId, CreateAddressRequest request);
    AddressResponse updateAddress(Long userId, Long addressId, CreateAddressRequest request);
    void deleteAddress(Long userId, Long addressId);
}
