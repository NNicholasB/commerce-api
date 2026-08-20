package io.github.nbgraciano.commerce_api.controller;


import io.github.nbgraciano.commerce_api.entity.dto.Users.UsersRequestDTO;
import io.github.nbgraciano.commerce_api.entity.dto.Users.UsersResponseDTO;
import io.github.nbgraciano.commerce_api.service.UsersService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/user")
public class UsersController {

    private final UsersService service;

    @PostMapping("/register")
    public ResponseEntity<UsersResponseDTO> register(@Valid @RequestBody UsersRequestDTO request){
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(request));
    }
}
