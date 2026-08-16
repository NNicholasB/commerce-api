package io.github.nbgraciano.commerce_api.service;


import io.github.nbgraciano.commerce_api.entity.Role;
import io.github.nbgraciano.commerce_api.entity.Users;
import io.github.nbgraciano.commerce_api.entity.dto.Password.PasswordChargeDTO;
import io.github.nbgraciano.commerce_api.entity.dto.Users.UsersRequestDTO;
import io.github.nbgraciano.commerce_api.entity.dto.Users.UsersResponseDTO;
import io.github.nbgraciano.commerce_api.entity.dto.role.ChargeRoleDTO;
import io.github.nbgraciano.commerce_api.entity.mappers.UsersMapper;
import io.github.nbgraciano.commerce_api.exception.DuplicateEntityException;
import io.github.nbgraciano.commerce_api.exception.EntityNotFoundException;
import io.github.nbgraciano.commerce_api.exception.InvalidPasswordException;
import io.github.nbgraciano.commerce_api.repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UsersService {

    private final UsersRepository repository;
    private final UsersMapper mapper;
    private final PasswordEncoder passwordEncoder;


    public UsersResponseDTO create(UsersRequestDTO request){
        if (repository.existsByNameAndEmail(request.name(),request.email())){
            throw new DuplicateEntityException("Users already exists");
        }
        Users user=mapper.toEntity(request);
        user.setName(request.name());
        user.setEmail(request.email());
        user.setPassword(passwordEncoder.encode(request.password()));
        user.setRole(Role.USER);
        Users save = repository.save(user);
        return mapper.toResponse(save);
    }

    public UsersResponseDTO findById(UUID id){
        Users user=repository.findById(id).orElseThrow(()->
                new EntityNotFoundException("User not found"));

        return mapper.toResponse(user);
        }

    public List<UsersRequestDTO> findAll(){
        return mapper.toResponse(repository.findAll());
    }

    public void delete(UUID id){
        Users user=repository.findById(id).orElseThrow(()->
                new EntityNotFoundException("User not found"));

        repository.delete(user);
    }

    public UsersResponseDTO update(UUID id, UsersRequestDTO request){
        Users user=repository.findById(id).orElseThrow(()->
                new EntityNotFoundException("User not found"));

        user.setEmail(request.email());
        user.setName(request.name());


        return mapper.toResponse(repository.save(user));
    }

    public void chargePassword(UUID id, PasswordChargeDTO request){
        Users user=repository.findById(id).orElseThrow(()-> new EntityNotFoundException("User not found"));
        if (!passwordEncoder.matches(request.currentPassword(), user.getPassword())) {
            throw  new InvalidPasswordException("Current password is incorrect");
        }

        user.setPassword(passwordEncoder.encode(request.newPassword()));
        repository.save(user);
    };

    public UsersResponseDTO chargeRole(UUID id, ChargeRoleDTO request){
        Users user=repository.findById(id).orElseThrow(()-> new EntityNotFoundException("User not found"));
        user.setRole(request.role());
        return mapper.toResponse(repository.save(user));
    }
}
