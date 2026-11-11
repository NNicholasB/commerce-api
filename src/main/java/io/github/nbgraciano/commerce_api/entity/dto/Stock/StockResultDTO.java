package io.github.nbgraciano.commerce_api.entity.dto.Stock;

import java.util.UUID;

public record StockResultDTO(
        UUID eventId,
        UUID orderId,
        String status
) {
}
