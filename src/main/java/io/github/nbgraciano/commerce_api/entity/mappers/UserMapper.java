package io.github.nbgraciano.commerce_api.entity.mappers;


import io.github.nbgraciano.commerce_api.entity.Users;
import io.github.nbgraciano.commerce_api.entity.dto.Users.UsersRequestDTO;
import io.github.nbgraciano.commerce_api.entity.dto.Users.UsersResponseDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UsersResponseDTO toResponse(Users users);

    Users toEntity(UsersRequestDTO request);
}
