package com.ecommerce.service.users.impl;

import com.ecommerce.mapper.users.UserMapper;
import com.ecommerce.dto.users.request.CreateAddressRequestDTO;
import com.ecommerce.dto.users.request.UpdateUserRequestDTO;
import com.ecommerce.dto.users.response.AddressResponseDTO;
import com.ecommerce.dto.users.response.UserResponseDTO;
import com.ecommerce.entity.users.Address;
import com.ecommerce.entity.users.User;
import com.ecommerce.exception.ResourceNotFoundException;
import com.ecommerce.repository.users.AddressRepository;
import com.ecommerce.repository.users.UserRepository;
import com.ecommerce.service.users.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    AddressRepository addressRepository;

    @Autowired
    UserMapper userMapper;

    @Override
    @Transactional(readOnly = true)
    public UserResponseDTO getUserById(Long userId) {
        log.info("Fetching user with ID: {}", userId);
        User user = userRepository.findByIdWithAddresses(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));
        return userMapper.toResponse(user);
    }

    @Override
    public UserResponseDTO updateUser(Long userId, UpdateUserRequestDTO request) {
        log.info("Updating user with ID: {}", userId);
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));
        if (request.getEmail() != null && !request.getEmail().equals(user.getEmail())) {
            if (userRepository.existsByEmailAndIdNot(request.getEmail(), userId)) {
                throw new IllegalArgumentException("Email is already taken: " + request.getEmail());
            }
            user.setEmail(request.getEmail());
        }
        if (request.getFirstName() != null) {
            user.setFirstName(request.getFirstName());
        }
        if (request.getLastName() != null) {
            user.setLastName(request.getLastName());
        }
        if (request.getPhoneNumber() != null) {
            user.setPhoneNumber(request.getPhoneNumber());
        }
        User updatedUser = userRepository.save(user);
        log.info("Updated user with ID: {}", userId);
        return userMapper.toResponse(updatedUser);
    }

    @Override
    public void deleteUser(Long userId) {
        log.info("Deleting user with ID: {}", userId);
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));
        userRepository.delete(user);
        log.info("Deleted user with ID: {}", userId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<AddressResponseDTO> getUserAddresses(Long userId) {
        log.info("Fetching addresses for user ID: {}", userId);
        if (!userRepository.existsById(userId)) {
            throw new ResourceNotFoundException("User not found with id: " + userId);
        }
        List<Address> addresses = addressRepository.findByUserId(userId);
        return userMapper.toAddressResponseList(addresses);
    }

    @Override
    public AddressResponseDTO addAddress(Long userId, CreateAddressRequestDTO request) {
        log.info("Adding address for user ID: {}", userId);
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));
        Address address = Address.builder()
                .user(user)
                .street(request.getStreet())
                .city(request.getCity())
                .state(request.getState())
                .postalCode(request.getPostalCode())
                .country(request.getCountry())
                .build();
        user.addAddress(address);
        Address savedAddress = addressRepository.save(address);
        log.info("Added address with ID: {} for user ID: {}", savedAddress.getId(), userId);
        return userMapper.toAddressResponse(savedAddress);
    }

    @Override
    public AddressResponseDTO updateAddress(Long userId, Long addressId, CreateAddressRequestDTO request) {
        log.info("Updating address ID: {} for user ID: {}", addressId, userId);
        Address address = addressRepository.findByIdAndUserId(addressId, userId)
                .orElseThrow(() -> new ResourceNotFoundException("Address not found with id: " + addressId + " for user: " + userId));
        address.setStreet(request.getStreet());
        address.setCity(request.getCity());
        address.setState(request.getState());
        address.setPostalCode(request.getPostalCode());
        address.setCountry(request.getCountry());
        Address updatedAddress = addressRepository.save(address);
        log.info("Updated address ID: {} for user ID: {}", addressId, userId);
        return userMapper.toAddressResponse(updatedAddress);
    }

    @Override
    public void deleteAddress(Long userId, Long addressId) {
        log.info("Deleting address ID: {} for user ID: {}", addressId, userId);
        Address address = addressRepository.findByIdAndUserId(addressId, userId)
                .orElseThrow(() -> new ResourceNotFoundException("Address not found with id: " + addressId + " for user: " + userId));
        addressRepository.delete(address);
        log.info("Deleted address ID: {} for user ID: {}", addressId, userId);
    }
}
