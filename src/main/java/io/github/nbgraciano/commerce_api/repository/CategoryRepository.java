package io.github.nbgraciano.commerce_api.repository;

import io.github.nbgraciano.commerce_api.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface CategoryRepository extends JpaRepository<Category,UUID> {

    boolean existsByName(String name);
    boolean existsByNameAndIdNot(String name, UUID id);
    List<Category> findByNameContainingIgnoreCase(String name);
}
