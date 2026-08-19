package io.github.nbgraciano.commerce_api.entity.mappers;


import io.github.nbgraciano.commerce_api.entity.Order;
import io.github.nbgraciano.commerce_api.entity.dto.Order.OrderRequestDTO;
import io.github.nbgraciano.commerce_api.entity.dto.Order.OrderResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring",  uses = OrderItemMapper.class)
public interface OrderMapper {

    @Mapping(target = "userId", source = "user.id")
    OrderResponseDTO toResponse(Order order);

    List<OrderResponseDTO> toResponse(List<Order> list);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "total", ignore = true)
    Order toEntity(OrderRequestDTO request);


}
