package io.github.nbgraciano.commerce_api.service;

import io.github.nbgraciano.commerce_api.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private CategoryRepository repository;


}
