package io.github.nbgraciano.commerce_api.entity.dto.Users;

import java.util.UUID;

public record UsersResponseDTO(
        UUID id,
        String name,
        String email,
        String role
) {
}
