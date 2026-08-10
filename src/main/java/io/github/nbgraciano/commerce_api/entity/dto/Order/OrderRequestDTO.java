package io.github.nbgraciano.commerce_api.entity.dto.Order;

import io.github.nbgraciano.commerce_api.entity.Status;
import io.github.nbgraciano.commerce_api.entity.Users;
import jakarta.validation.constraints.NotBlank;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public record OrderRequestDTO(
        @NotBlank(message = "User obligatory")
        Users usersId,
        List items

) {
}
