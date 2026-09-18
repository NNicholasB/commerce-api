package io.github.nbgraciano.commerce_api.service;

import io.github.nbgraciano.commerce_api.entity.Role;
import io.github.nbgraciano.commerce_api.entity.Users;
import io.github.nbgraciano.commerce_api.entity.dto.Users.UsersRequestDTO;
import io.github.nbgraciano.commerce_api.entity.dto.Users.UsersResponseDTO;
import io.github.nbgraciano.commerce_api.entity.mappers.UsersMapper;
import io.github.nbgraciano.commerce_api.exception.DuplicateEntityException;
import io.github.nbgraciano.commerce_api.exception.EntityNotFoundException;
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
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

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

    @Test
    @DisplayName("Deve lancar excecao ao nao encontrar User pelo id")
    void erroFindById(){
        UUID userId=UUID.randomUUID();
        Users user=new Users(userId,"Nicholas","nic@gmail.com","12345678", Role.USER);
        UsersResponseDTO responseDTO= new UsersResponseDTO(userId,"Nicholas","nic@gmail.com","12345678");
        when(repository.findById(userId)).thenReturn(Optional.empty());

        EntityNotFoundException ex = assertThrows(EntityNotFoundException.class, () -> service.findById(userId));

        assertEquals("User not found",ex.getMessage());

        verify(mapper,never()).toResponse(user);
    }

    @Test
    @DisplayName("Deve criar User")
    void create(){
        UUID userId=UUID.randomUUID();
        Users user=new Users(userId,"Nicholas","nic@gmail.com","12345678", Role.USER);
        UsersRequestDTO requestDTO= new UsersRequestDTO("Nicholas","12345678","nic@gmial.com");
        UsersResponseDTO responseDTO= new UsersResponseDTO(userId,"Nicholas","nic@gmail.com","12345678");

        when(repository.existsByNameAndEmail(requestDTO.name(),requestDTO.email())).thenReturn(false);
        when(mapper.toResponse(user)).thenReturn(responseDTO);
        when(encoder.encode(requestDTO.password())).thenReturn(requestDTO.password());
        when(repository.save(user)).thenReturn(user);
        when(mapper.toEntity(requestDTO)).thenReturn(user);

        UsersResponseDTO result=service.create(requestDTO);

        assertEquals("Nicholas",result.name());
        assertEquals("nic@gmail.com",result.email());

        verify(mapper).toResponse(user);

    }

    @Test
    @DisplayName("Deve lancar excecao quando ja existir por nome e email")
    void erroCreate(){
        UUID userId=UUID.randomUUID();
        Users user=new Users(userId,"Nicholas","nic@gmail.com","12345678", Role.USER);
        UsersRequestDTO requestDTO= new UsersRequestDTO("Nicholas","12345678","nic@gmial.com");

        when(repository.existsByNameAndEmail(requestDTO.name(),requestDTO.email())).thenReturn(true);

        DuplicateEntityException ex = assertThrows(DuplicateEntityException.class, () -> service.create(requestDTO));

        assertEquals("Users already exists",ex.getMessage());

        verify(repository,never()).save(user);
        verify(mapper,never()).toResponse(user);
    }

    @Test
    @DisplayName("Deve deletar normal pelo Id")
    void delete(){
        UUID userId=UUID.randomUUID();
        Users user=new Users(userId,"Nicholas","nic@gmail.com","12345678", Role.USER);

        when(repository.findById(userId)).thenReturn(Optional.of(user));

        service.delete(userId);

        verify(repository).findById(userId);

    }
}
