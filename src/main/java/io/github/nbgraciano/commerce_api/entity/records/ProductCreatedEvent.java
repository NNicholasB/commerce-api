package io.github.nbgraciano.commerce_api.entity.records;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

import java.util.UUID;

public record ProductCreatedEvent(

        @NotNull(message = "ProductId is required")
        UUID id,

        @PositiveOrZero(message = "Stock not can be negative")
        Integer stock
) {
}
