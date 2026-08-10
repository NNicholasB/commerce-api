package io.github.nbgraciano.commerce_api.entity.mappers;

import io.github.nbgraciano.commerce_api.entity.Product;
import io.github.nbgraciano.commerce_api.entity.dto.Product.ProductRequestDTO;
import io.github.nbgraciano.commerce_api.entity.dto.Product.ProductResponseDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    ProductResponseDTO toResponse(Product product);

    Product toEntity(ProductRequestDTO request);
}
