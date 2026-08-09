package io.github.nbgraciano.commerce_api.entity.dto.Order;

import io.github.nbgraciano.commerce_api.entity.Status;
import io.github.nbgraciano.commerce_api.entity.Users;

import java.math.BigDecimal;
import java.util.UUID;

public record OrderResponseDTO(
        UUID id,
        Users userId,
        Status status,
        BigDecimal total,
            List<OrderItemResponseDTO> items
) {}