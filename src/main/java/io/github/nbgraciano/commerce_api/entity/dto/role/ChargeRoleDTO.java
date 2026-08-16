package io.github.nbgraciano.commerce_api.entity.dto.role;

import io.github.nbgraciano.commerce_api.entity.Role;
import jakarta.validation.constraints.NotNull;

public record ChargeRoleDTO(
        @NotNull
        Role role
) {
}
