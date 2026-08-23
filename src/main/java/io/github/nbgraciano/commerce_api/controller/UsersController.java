package io.github.nbgraciano.commerce_api.controller;


import io.github.nbgraciano.commerce_api.entity.dto.Password.PasswordChangeDTO;
import io.github.nbgraciano.commerce_api.entity.dto.Role.ChangeRoleDTO;
import io.github.nbgraciano.commerce_api.entity.dto.Users.UsersRequestDTO;
import io.github.nbgraciano.commerce_api.entity.dto.Users.UsersResponseDTO;
import io.github.nbgraciano.commerce_api.service.UsersService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("/users")
public class UsersController {

    private final UsersService service;

    @PostMapping("/register")
    public ResponseEntity<UsersResponseDTO> register(@Valid @RequestBody UsersRequestDTO request){
        UsersResponseDTO response = service.create(request);
        URI uri= ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(response.id())
                .toUri();
        return ResponseEntity.created(uri).body(response);
    }

    @GetMapping()
    public ResponseEntity<List<UsersResponseDTO>> findAll(@RequestParam(required = false) String name,@RequestParam(required = false)String email){
        List<UsersResponseDTO> lista= service.findAll(name, email);
        return ResponseEntity.ok().body(lista);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsersResponseDTO> findById(@PathVariable UUID id){
        return ResponseEntity.ok().body(service.findById(id));
    }

    @DeleteMapping("{id}")
    public void deleteById(@Valid @PathVariable UUID id){
        service.delete(id);

    }

    @PutMapping("/{id}")
    public ResponseEntity<UsersResponseDTO> update(@PathVariable UUID id,@Valid @RequestBody UsersRequestDTO request){
        UsersResponseDTO response=service.update(id,request);
        URI uri= ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(response.id())
                .toUri();
        return ResponseEntity.created(uri).body(response);
    }

    @PatchMapping("/{id}/password")
    public ResponseEntity<Void> chargePassword(@PathVariable UUID id, @Valid @RequestBody PasswordChangeDTO request){
        service.changePassword(id,request);
        return  ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/role")
    public ResponseEntity<Void> chargeRole(@PathVariable UUID id,@Valid @RequestBody ChangeRoleDTO request){
        service.changeRole(id,request);
        return ResponseEntity.noContent().build();
    }
}
