package io.github.nbgraciano.commerce_api.entity.mappers;

import io.github.nbgraciano.commerce_api.entity.OrderItem;
import io.github.nbgraciano.commerce_api.entity.dto.OrderItem.OrderItemRequestDTO;
import io.github.nbgraciano.commerce_api.entity.dto.OrderItem.OrderItemResponseDTO;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface OrderItemMapper {

    OrderItemResponseDTO toResponse(OrderItem orderItem);

    List<OrderItemResponseDTO> toResponse(List<OrderItem> list);

    OrderItem toEntity(OrderItemRequestDTO request);
}
