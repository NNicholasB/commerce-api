package io.github.nbgraciano.commerce_api.entity.dto.OrderItem;

import java.util.UUID;

public record OrderItemRequestDTO(
        UUID productId,
        Integer quantity

) {}
