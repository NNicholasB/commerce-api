package io.github.nbgraciano.commerce_api.service;

import io.github.nbgraciano.commerce_api.entity.Category;
import io.github.nbgraciano.commerce_api.entity.Product;
import io.github.nbgraciano.commerce_api.entity.dto.Product.ProductRequestDTO;
import io.github.nbgraciano.commerce_api.entity.dto.Product.ProductResponseDTO;
import io.github.nbgraciano.commerce_api.entity.mappers.ProductMapper;
import io.github.nbgraciano.commerce_api.exception.DuplicateEntityException;
import io.github.nbgraciano.commerce_api.exception.EntityNotFoundException;
import io.github.nbgraciano.commerce_api.repository.CategoryRepository;
import io.github.nbgraciano.commerce_api.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository repository;
    private final CategoryRepository categoryRepository;
    private final ProductMapper mapper;

    public ProductResponseDTO create(ProductRequestDTO request){
        if (repository.existsByNameAndCategoryId(request.name(),request.categoryId())){
            throw new DuplicateEntityException("Product already exists");
        }

       Product product=mapper.toEntity(request);
        return mapper.toResponse(repository.save(product));

    }

    public ProductResponseDTO findById(UUID id){
        return mapper.toResponse(repository.findById(id).orElseThrow(()->
                new EntityNotFoundException("Product not found")));
    }

    public List<ProductResponseDTO> findAll(){
        return mapper.toResponse(repository.findAll());
    }

    public void delete(UUID id){
        Product product=repository.findById(id).orElseThrow(()->
                new EntityNotFoundException("Product not found"));
        repository.delete(product);
    }

    public ProductResponseDTO update(UUID id,ProductRequestDTO request){
        if (repository.existsByNameAndCategoryIdAndIdNot(request.name(),request.categoryId(),id)){
            throw new DuplicateEntityException("Product already exists");
        }

        Product product=repository.findById(id).orElseThrow(()->
                new EntityNotFoundException("Product not found"));

        product.setName(request.name());
        product.setStock(request.stock());
        product.setDescription(request.description());
        product.setCategory(categoryRepository.findById(request.categoryId()).orElseThrow(()->
                new EntityNotFoundException("Category not found")));
        product.setPrice(request.price());

        return mapper.toResponse(repository.save(product));

    }
}
