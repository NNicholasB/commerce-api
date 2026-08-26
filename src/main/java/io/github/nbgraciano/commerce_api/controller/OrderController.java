package io.github.nbgraciano.commerce_api.controller;


import io.github.nbgraciano.commerce_api.entity.dto.Order.OrderRequestDTO;
import io.github.nbgraciano.commerce_api.entity.dto.Order.OrderResponseDTO;
import io.github.nbgraciano.commerce_api.service.OrderService;
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
@RequestMapping("/order")
public class OrderController {

    private final OrderService service;

    @GetMapping()
    public ResponseEntity<List<OrderResponseDTO>> findAll(){
        return ResponseEntity.ok().body(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderResponseDTO> findById(@PathVariable UUID id){
        return ResponseEntity.ok().body(service.findById(id));
    }

    @PostMapping()
    public ResponseEntity<OrderResponseDTO> create(@Valid @RequestBody OrderRequestDTO request){
        OrderResponseDTO created = service.create(request);
        URI uri= ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(created.id())
                .toUri();
        return ResponseEntity.created(uri).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<OrderResponseDTO> update(@PathVariable UUID id,@Valid @RequestBody OrderRequestDTO request){
        return ResponseEntity.ok().body(service.update(id,request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable UUID id ){
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/pay")
    public ResponseEntity<OrderResponseDTO>pay(@PathVariable UUID id){
        return ResponseEntity.ok().body(service.pay(id));
    }

    @PatchMapping("/{id}/cancel")
    public ResponseEntity<OrderResponseDTO>cancel(@PathVariable UUID id){
        return ResponseEntity.ok().body(service.cancel(id));
    }

    @PatchMapping("/{id}/ship")
    public ResponseEntity<OrderResponseDTO>ship(@PathVariable UUID id){
        return ResponseEntity.ok().body(service.ship(id));
    }

    @PatchMapping("/{id}/deliver")
    public ResponseEntity<OrderResponseDTO>deliver(@PathVariable UUID id){
        return ResponseEntity.ok().body(service.deliver(id));
    }


}
