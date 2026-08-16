package io.github.nbgraciano.commerce_api.entity.mappers;


import io.github.nbgraciano.commerce_api.entity.Users;
import io.github.nbgraciano.commerce_api.entity.dto.Users.UsersRequestDTO;
import io.github.nbgraciano.commerce_api.entity.dto.Users.UsersResponseDTO;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UsersMapper {

    UsersResponseDTO toResponse(Users users);

    List<UsersRequestDTO> toResponse(List<Users> list);

    Users toEntity(UsersRequestDTO request);
}
