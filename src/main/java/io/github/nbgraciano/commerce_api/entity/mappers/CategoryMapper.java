package io.github.nbgraciano.commerce_api.entity.mappers;

import io.github.nbgraciano.commerce_api.entity.Category;
import io.github.nbgraciano.commerce_api.entity.dto.Category.CategoryRequestDTO;
import io.github.nbgraciano.commerce_api.entity.dto.Category.CategoryResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CategoryMapper {

    CategoryResponseDTO toResponse(Category category);

    List<CategoryResponseDTO> toResponse(List<Category> list);

    @Mapping(target = "id", ignore = true)
    Category toEntity(CategoryRequestDTO request);
}
