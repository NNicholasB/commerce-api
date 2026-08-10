package io.github.nbgraciano.commerce_api.entity.dto.Product;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.util.UUID;

public record ProductRequestDTO(

        @NotBlank(message = "Name obligatory")
        @Size(max = 100,message = "The name must be no more than 100 characters")
        String name,
        @NotBlank(message = "Description obligatory")
        @Size(max = 150,message = "The name must be no more than 150 characters")
        String description,
        @NotBlank(message = "Price obligatory")
        BigDecimal price,
        @NotBlank(message = "Stock obligatory")
        Integer stock,
        @NotBlank(message = "Category obligatory")
        UUID categoryId
) {}
