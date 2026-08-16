package io.github.nbgraciano.commerce_api.entity.dto.Password;

import jakarta.validation.constraints.NotBlank;

public record PasswordChargeDTO(

        @NotBlank(message = "Current password is required")
        String currentPassword,

        @NotBlank(message = "New password is required")
        String newPassword
) {
}
