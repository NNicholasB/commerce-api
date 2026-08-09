package io.github.nbgraciano.commerce_api.repository;

import io.github.nbgraciano.commerce_api.entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface OrderItemRepository extends JpaRepository<OrderItem, UUID> {
}
