package io.github.nbgraciano.commerce_api.entity.dto.Role;

import io.github.nbgraciano.commerce_api.entity.Role;
import jakarta.validation.constraints.NotNull;

public record ChangeRoleDTO(
        @NotNull
        Role role
) {
}
