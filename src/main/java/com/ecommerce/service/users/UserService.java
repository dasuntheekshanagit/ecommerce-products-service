package com.ecommerce.service.users;

import com.ecommerce.dto.users.request.CreateAddressRequestDTO;
import com.ecommerce.dto.users.request.UpdateUserRequestDTO;
import com.ecommerce.dto.users.response.AddressResponseDTO;
import com.ecommerce.dto.users.response.UserResponseDTO;

import java.util.List;

public interface UserService {
    UserResponseDTO getUserById(Long userId);
    UserResponseDTO updateUser(Long userId, UpdateUserRequestDTO request);
    void deleteUser(Long userId);
    List<AddressResponseDTO> getUserAddresses(Long userId);
    AddressResponseDTO addAddress(Long userId, CreateAddressRequestDTO request);
    AddressResponseDTO updateAddress(Long userId, Long addressId, CreateAddressRequestDTO request);
    void deleteAddress(Long userId, Long addressId);
    UserResponseDTO createUser(UpdateUserRequestDTO request);
}
