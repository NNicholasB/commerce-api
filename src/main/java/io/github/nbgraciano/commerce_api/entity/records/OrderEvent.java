package io.github.nbgraciano.commerce_api.entity.records;

import java.math.BigDecimal;
import java.util.UUID;

public record OrderEvent(
        UUID orderId,
        UUID userId,
        BigDecimal total,
        String status
) {
}
