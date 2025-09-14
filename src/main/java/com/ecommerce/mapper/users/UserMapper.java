package com.ecommerce.mapper.users;

import com.ecommerce.dto.users.response.AddressResponseDTO;
import com.ecommerce.dto.users.response.UserResponseDTO;
import com.ecommerce.entity.users.Address;
import com.ecommerce.entity.users.User;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    @Mapping(target = "fullName", source = "fullName")
    @Mapping(target = "addresses", source = "addresses")
    UserResponseDTO toResponse(User user);

    AddressResponseDTO toAddressResponse(Address address);

    List<AddressResponseDTO> toAddressResponseList(List<Address> addresses);
}
