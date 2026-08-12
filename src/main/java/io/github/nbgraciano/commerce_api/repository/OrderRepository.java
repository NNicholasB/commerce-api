package io.github.nbgraciano.commerce_api.repository;

import io.github.nbgraciano.commerce_api.entity.Order;
import io.github.nbgraciano.commerce_api.entity.dto.OrderItem.OrderItemRequestDTO;
import jakarta.validation.Valid;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface OrderRepository extends JpaRepository<Order, UUID> {
}
