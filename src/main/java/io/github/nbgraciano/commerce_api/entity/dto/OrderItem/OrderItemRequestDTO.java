package io.github.nbgraciano.commerce_api.entity.dto.OrderItem;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.UUID;

public record OrderItemRequestDTO(
        @NotBlank(message = "Id obligatory")
        UUID productId,
        @Size(min = 1,message = "The quantity must be greater than 0")
        Integer quantity

) {}
