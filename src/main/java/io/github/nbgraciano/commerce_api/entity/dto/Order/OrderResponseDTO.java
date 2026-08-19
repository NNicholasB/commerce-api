package io.github.nbgraciano.commerce_api.entity.dto.Order;

import io.github.nbgraciano.commerce_api.entity.Status;
import io.github.nbgraciano.commerce_api.entity.dto.OrderItem.OrderItemResponseDTO;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public record OrderResponseDTO(
        UUID id,
        UUID userId,
        Status status,
        BigDecimal total,
        List<OrderItemResponseDTO> items
) {}