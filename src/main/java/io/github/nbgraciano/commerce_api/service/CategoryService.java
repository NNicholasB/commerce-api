package io.github.nbgraciano.commerce_api.service;

import io.github.nbgraciano.commerce_api.entity.Category;
import io.github.nbgraciano.commerce_api.entity.dto.Category.CategoryRequestDTO;
import io.github.nbgraciano.commerce_api.entity.dto.Category.CategoryResponseDTO;
import io.github.nbgraciano.commerce_api.entity.mappers.CategoryMapper;
import io.github.nbgraciano.commerce_api.exception.DuplicateEntityException;
import io.github.nbgraciano.commerce_api.exception.EntityNotFoundException;
import io.github.nbgraciano.commerce_api.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import java.util.UUID;


@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository repository;

    private final CategoryMapper mapper;

    public CategoryResponseDTO create(CategoryRequestDTO request){

        if (repository.existsByName(request.name())) {
            throw new DuplicateEntityException("category already exists");
        }
        Category category = mapper.toEntity(request);
        Category saved = repository.save(category);
        return mapper.toResponse(saved);
    }

    public CategoryResponseDTO findById(UUID id) {
        Category categoryAchada = repository.findById(id)
                .orElseThrow(() ->
                        new EntityNotFoundException("Category not found")
                );

        return mapper.toResponse(categoryAchada);
    }

    public List<CategoryResponseDTO> findAll(){

        return mapper.toResponse(repository.findAll());
    }

    public void deleteById(UUID id){
        Category category=repository.findById(id).orElseThrow(()->
            new EntityNotFoundException("Category not found")
        );
       repository.delete(category);

    }

    public CategoryResponseDTO update(UUID id,CategoryRequestDTO request){
        Category category=repository.findById(id).orElseThrow(()->
                new EntityNotFoundException("Category not found")
        );
        if (repository.existsByNameAndIdNot(request.name(), id)) {
            throw new DuplicateEntityException("Category already exists");
        }
        category.setName(request.name());
        return mapper.toResponse(repository.save(category));
    }

}
