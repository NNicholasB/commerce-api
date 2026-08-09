package io.github.nbgraciano.commerce_api.entity.dto.Product;

import io.github.nbgraciano.commerce_api.entity.dto.Category.CategoryResponseDTO;

import java.math.BigDecimal;
import java.util.UUID;

public record ProductResponseDTO(
        UUID id,
        String name,
        String description,
        BigDecimal price,
        Integer stock,
        CategoryResponseDTO category

) {
}
