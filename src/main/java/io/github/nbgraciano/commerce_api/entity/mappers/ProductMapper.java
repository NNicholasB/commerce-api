package io.github.nbgraciano.commerce_api.entity.mappers;

import io.github.nbgraciano.commerce_api.entity.Order;
import io.github.nbgraciano.commerce_api.entity.Product;
import io.github.nbgraciano.commerce_api.entity.dto.Order.OrderResponseDTO;
import io.github.nbgraciano.commerce_api.entity.dto.Product.ProductRequestDTO;
import io.github.nbgraciano.commerce_api.entity.dto.Product.ProductResponseDTO;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    ProductResponseDTO toResponse(Product product);

    List<ProductResponseDTO> toResponse(List<Product> list);


    Product toEntity(ProductRequestDTO request);
}
