package io.github.nbgraciano.commerce_api.entity.mappers;


import io.github.nbgraciano.commerce_api.entity.Users;
import io.github.nbgraciano.commerce_api.entity.dto.Users.UsersRequestDTO;
import io.github.nbgraciano.commerce_api.entity.dto.Users.UsersResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UsersMapper {

    UsersResponseDTO toResponse(Users users);

    List<UsersResponseDTO> toResponse(List<Users> list);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "role", ignore = true)
    Users toEntity(UsersRequestDTO request);
}
