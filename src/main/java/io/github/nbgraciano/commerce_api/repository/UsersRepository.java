package io.github.nbgraciano.commerce_api.repository;

import io.github.nbgraciano.commerce_api.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UsersRepository extends JpaRepository<Users, UUID> {

    boolean existsByNameAndEmail(String name,String email);

    List<Users> findByNameContainingIgnoreCase(String name);

    List<Users> findByEmailContainingIgnoreCase(String email);

    Optional<Users> findByEmail(String email);
}
