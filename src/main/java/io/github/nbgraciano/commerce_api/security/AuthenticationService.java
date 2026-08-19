package io.github.nbgraciano.commerce_api.security;


import io.github.nbgraciano.commerce_api.entity.dto.login.LoginRequestDTO;
import io.github.nbgraciano.commerce_api.entity.dto.login.LoginResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {


    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public LoginResponseDTO login(LoginRequestDTO request){
        Authentication authentication= authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.email(),request.password())
        );
        String token=jwtService.generateToken(authentication);
        return new LoginResponseDTO(token);
    }

}
