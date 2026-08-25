package io.github.nbgraciano.commerce_api.controller;


import io.github.nbgraciano.commerce_api.entity.dto.Product.ProductRequestDTO;
import io.github.nbgraciano.commerce_api.entity.dto.Product.ProductResponseDTO;
import io.github.nbgraciano.commerce_api.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/product")
public class ProductController {

    private final ProductService service;

    @GetMapping()
    public ResponseEntity<List<ProductResponseDTO>> findAll(@RequestParam(required = false) String name,@RequestParam(required = false) String category){
        List<ProductResponseDTO> lista = service.findAll(name,category);
        return ResponseEntity.ok().body(lista);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductResponseDTO> findById(@PathVariable UUID id){
        return ResponseEntity.ok().body(service.findById(id));
    }

    @PostMapping
    public ResponseEntity<ProductResponseDTO> crate(@Valid @RequestBody ProductRequestDTO request){
        ProductResponseDTO response = service.create(request);
        URI uri= ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(response.id()).toUri();

        return ResponseEntity.created(uri).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductResponseDTO> update(@PathVariable UUID id,@Valid @RequestBody ProductRequestDTO request){
        return ResponseEntity.ok().body(service.update(id,request));
    }

   @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable UUID id ){
        service.delete(id);
        return ResponseEntity.noContent().build();
   }

}
