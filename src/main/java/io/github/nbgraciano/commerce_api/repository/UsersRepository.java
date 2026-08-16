package io.github.nbgraciano.commerce_api.repository;

import io.github.nbgraciano.commerce_api.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UsersRepository extends JpaRepository<Users, UUID> {

    boolean existsByNameAndEmail(String name,String email);

}
