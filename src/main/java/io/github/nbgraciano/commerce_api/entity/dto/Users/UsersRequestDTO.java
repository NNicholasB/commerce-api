package io.github.nbgraciano.commerce_api.entity.dto.Users;

public record UsersRequestDTO(
        String name,
        String password,
        String email,
        String role
) {}
