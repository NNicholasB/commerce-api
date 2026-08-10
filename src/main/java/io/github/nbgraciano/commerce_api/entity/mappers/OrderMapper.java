package io.github.nbgraciano.commerce_api.entity.mappers;


import io.github.nbgraciano.commerce_api.entity.Order;
import io.github.nbgraciano.commerce_api.entity.dto.Order.OrderRequestDTO;
import io.github.nbgraciano.commerce_api.entity.dto.Order.OrderResponseDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface OrderMapper {

    OrderResponseDTO toResponse(Order order);

    Order toEntity(OrderRequestDTO request);


}
