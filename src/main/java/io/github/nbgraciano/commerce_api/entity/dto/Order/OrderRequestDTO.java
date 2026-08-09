package io.github.nbgraciano.commerce_api.entity.dto.Order;

import io.github.nbgraciano.commerce_api.entity.Status;
import io.github.nbgraciano.commerce_api.entity.Users;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public record OrderRequestDTO(

        Users usersId,
        List items

) {
}
