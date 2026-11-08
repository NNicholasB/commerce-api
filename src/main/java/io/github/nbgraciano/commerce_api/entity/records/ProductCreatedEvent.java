package io.github.nbgraciano.commerce_api.entity.records;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

import java.util.UUID;

public record ProductCreatedEvent(


        UUID eventId,

        UUID productId,

        @PositiveOrZero(message = "Stock not can be negative")
        Integer stock
) {
}
