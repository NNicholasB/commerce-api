package io.github.nbgraciano.commerce_api.service;

import io.github.nbgraciano.commerce_api.entity.Category;
import io.github.nbgraciano.commerce_api.entity.dto.Category.CategoryRequestDTO;
import io.github.nbgraciano.commerce_api.entity.dto.Category.CategoryResponseDTO;
import io.github.nbgraciano.commerce_api.entity.mappers.CategoryMapper;
import io.github.nbgraciano.commerce_api.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private CategoryRepository repository;

    private CategoryMapper mapper;

    public CategoryResponseDTO create(CategoryRequestDTO request){
        Category category=mapper.toEntity(request);
        Category saved=repository.save(category);
        return mapper.toResponse(saved);
    }

}
