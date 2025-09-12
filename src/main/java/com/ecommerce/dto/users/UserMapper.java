package com.ecommerce.dto.users;

import com.ecommerce.dto.users.response.AddressResponse;
import com.ecommerce.dto.users.response.UserResponse;
import com.ecommerce.entity.users.Address;
import com.ecommerce.entity.users.User;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class UserMapper {

    public UserResponse toResponse(User user) {
        if (user == null) {
            return null;
        }

        List<AddressResponse> addressResponses = user.getAddresses()
                .stream()
                .map(this::toAddressResponse)
                .collect(Collectors.toList());

        return new UserResponse(
                user.getId(),
                user.getEmail(),
                user.getFirstName(),
                user.getLastName(),
                user.getPhoneNumber(),
                user.getFullName(),
                addressResponses,
                user.getCreatedAt(),
                user.getUpdatedAt()
        );
    }

    public AddressResponse toAddressResponse(Address address) {
        if (address == null) {
            return null;
        }

        return new AddressResponse(
                address.getId(),
                address.getUser() != null ? address.getUser().getId() : null,
                address.getStreet(),
                address.getCity(),
                address.getState(),
                address.getPostalCode(),
                address.getCountry(),
                address.getFullAddress()
        );
    }

    public List<AddressResponse> toAddressResponseList(List<Address> addresses) {
        if (addresses == null) {
            return null;
        }

        return addresses.stream()
                .map(this::toAddressResponse)
                .collect(Collectors.toList());
    }
}

