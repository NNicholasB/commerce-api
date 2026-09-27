package io.github.nbgraciano.commerce_api.entity.records;

import java.util.UUID;

public record OrderItemEvent(
        UUID productId,
        Integer quantity
) {
}
