package io.github.nbgraciano.commerce_api.entity.dto.login;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record LoginRequestDTO(

        @NotBlank(message = "Email is obligatory")
        @Email
        String email,
        @Size(max = 10,min = 2,message = "The password must be between 2 and 10 characters")
        String password
) {
}
