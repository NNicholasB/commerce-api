package io.github.nbgraciano.commerce_api.entity.dto.Users;

import jakarta.persistence.Entity;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UsersRequestDTO(

        @NotBlank(message = "Name is obligatory")
        @Size(max = 100,message = "The name must be no more than 100 characters")
        String name,

        @NotBlank(message = "Password is obligatory")
        @Size(min = 8, max = 100, message = "The password must be between 8 and 100 characters")
        String password,

        @NotBlank(message = "Email is required")
        @Email(message = "invalid email")
        String email

) {}
