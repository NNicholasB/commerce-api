package io.github.nbgraciano.commerce_api.entity.mappers;

import io.github.nbgraciano.commerce_api.entity.OrderItem;
import io.github.nbgraciano.commerce_api.entity.dto.OrderItem.OrderItemRequestDTO;
import io.github.nbgraciano.commerce_api.entity.dto.OrderItem.OrderItemResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface OrderItemMapper {

    @Mapping(target = "productId", source = "product.id")
    @Mapping(target = "productName", source = "product.name")
    OrderItemResponseDTO toResponse(OrderItem orderItem);

    List<OrderItemResponseDTO> toResponse(List<OrderItem> list);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "order", ignore = true)
    @Mapping(target = "product", ignore = true)
    @Mapping(target = "unitPrice", ignore = true)
    @Mapping(target = "subtotal", ignore = true)
    OrderItem toEntity(OrderItemRequestDTO request);
}
