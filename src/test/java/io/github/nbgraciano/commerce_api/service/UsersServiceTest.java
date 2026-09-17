package io.github.nbgraciano.commerce_api.service;

import io.github.nbgraciano.commerce_api.entity.Role;
import io.github.nbgraciano.commerce_api.entity.Users;
import io.github.nbgraciano.commerce_api.entity.dto.Users.UsersResponseDTO;
import io.github.nbgraciano.commerce_api.entity.mappers.UsersMapper;
import io.github.nbgraciano.commerce_api.repository.UsersRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UsersServiceTest {

    @Mock
    private UsersRepository repository;

    @Mock
    private UsersMapper mapper;

    @Mock
    private PasswordEncoder encoder;

    @InjectMocks
    private UsersService service;

    @Test
    @DisplayName("Deve buscar User pelo id")
    void findById(){
        UUID userId=UUID.randomUUID();
        Users user=new Users(userId,"Nicholas","nic@gmail.com","12345678", Role.USER);
        UsersResponseDTO responseDTO= new UsersResponseDTO(userId,"Nicholas","nic@gmail.com","12345678");
        when(repository.findById(userId)).thenReturn(Optional.of(user));
        when(mapper.toResponse(user)).thenReturn(responseDTO);

        UsersResponseDTO result= service.findById(userId);

        assertEquals("Nicholas",result.name());
        assertEquals("nic@gmail.com",result.email());

        verify(repository).findById(userId);
        verify(mapper).toResponse(user);
    }



}
