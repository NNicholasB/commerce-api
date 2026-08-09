package io.github.nbgraciano.commerce_api.entity.dto.Category;

import java.util.UUID;

public record CategoryResponseDTO(
        UUID id,
        String name
) {
}
