package io.github.nbgraciano.commerce_api.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private   UUID id;

    @ManyToOne
    @JoinColumn(name = "users_id")
    private  Users user;

    @Enumerated(EnumType.STRING)
    private Status status;

    private BigDecimal total;

   private List items;

}
