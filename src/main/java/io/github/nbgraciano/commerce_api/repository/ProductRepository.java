package io.github.nbgraciano.commerce_api.repository;

import io.github.nbgraciano.commerce_api.entity.Category;
import io.github.nbgraciano.commerce_api.entity.Product;
import io.github.nbgraciano.commerce_api.entity.Users;
import io.github.nbgraciano.commerce_api.entity.dto.Category.CategoryResponseDTO;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ProductRepository extends JpaRepository<Product, UUID> {


    boolean existsByNameAndCategoryIdAndIdNot(String name,UUID categoryId,UUID id);
    boolean existsByNameAndCategoryId(String name, UUID uuid);

    List<Product> findByNameContainingIgnoreCase(String name);

    List<Product> findByCategory_NameContainingIgnoreCase(String categoryName);
}
