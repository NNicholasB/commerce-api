package io.github.nbgraciano.commerce_api.controller;


import io.github.nbgraciano.commerce_api.entity.dto.login.LoginRequestDTO;
import io.github.nbgraciano.commerce_api.entity.dto.login.LoginResponseDTO;
import io.github.nbgraciano.commerce_api.security.AuthenticationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class LoginController {

    private final AuthenticationService authenticationService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@Valid @RequestBody LoginRequestDTO request){
        return ResponseEntity.ok(authenticationService.login(request));
    }
}
