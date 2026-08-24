package io.github.nbgraciano.commerce_api.controller;


import io.github.nbgraciano.commerce_api.entity.dto.Order.OrderRequestDTO;
import io.github.nbgraciano.commerce_api.entity.dto.OrderItem.OrderItemRequestDTO;
import io.github.nbgraciano.commerce_api.entity.dto.OrderItem.OrderItemResponseDTO;
import io.github.nbgraciano.commerce_api.service.OrderItemService;
import jakarta.validation.Valid;
import jakarta.websocket.server.PathParam;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/OrderItem")
public class OrderItemController {

    private final OrderItemService service;

    @GetMapping()
    public ResponseEntity<List<OrderItemResponseDTO>> findAll(){
        return  ResponseEntity.ok().body(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderItemResponseDTO> findById( @PathVariable UUID id){
        return ResponseEntity.ok().body(service.findById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable UUID id){
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<OrderItemResponseDTO> update(@PathVariable UUID id,@Valid @RequestBody OrderItemRequestDTO request){

        return ResponseEntity.ok().body(service.update(id, request));
    }
}
