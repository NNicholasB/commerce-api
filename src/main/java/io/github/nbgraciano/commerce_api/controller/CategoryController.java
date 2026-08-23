package io.github.nbgraciano.commerce_api.controller;


import io.github.nbgraciano.commerce_api.entity.dto.Category.CategoryRequestDTO;
import io.github.nbgraciano.commerce_api.entity.dto.Category.CategoryResponseDTO;
import io.github.nbgraciano.commerce_api.entity.dto.Users.UsersResponseDTO;
import io.github.nbgraciano.commerce_api.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("/category")
public class CategoryController {

    private final CategoryService service;

    @GetMapping()
    public ResponseEntity<List<CategoryResponseDTO>>  findAll(@RequestParam(required = false)String name){
        List<CategoryResponseDTO> lista=service.findAll(name);
        return ResponseEntity.ok().body(lista);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryResponseDTO> findById(UUID id){
        return ResponseEntity.ok().body(service.findById(id));
    }

    @PostMapping()
    public ResponseEntity<CategoryResponseDTO> create(CategoryRequestDTO request){
        CategoryResponseDTO response=service.create(request);
        URI uri= ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(response.id()).toUri();
        return ResponseEntity.created(uri).body(response);
    }

}
