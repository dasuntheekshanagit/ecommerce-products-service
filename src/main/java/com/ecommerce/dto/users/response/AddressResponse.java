package com.ecommerce.dto.users.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AddressResponse {

    private Long id;
    private Long userId;
    private String street;
    private String city;
    private String state;
    private String postalCode;
    private String country;
    private String fullAddress;
}

