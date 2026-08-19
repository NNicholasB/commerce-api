package io.github.nbgraciano.commerce_api.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private  UUID id;

    private  String name;

    private String email;

    private  String password;

    @Enumerated(EnumType.STRING)
    private   Role role;

}
