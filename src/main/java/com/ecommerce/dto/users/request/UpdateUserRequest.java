package com.ecommerce.dto.users.request;

import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateUserRequest {

    @Email(message = "Email should be valid")
    private String email;

    private String firstName;

    private String lastName;

    private String phoneNumber;
}

