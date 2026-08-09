package io.github.nbgraciano.commerce_api.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
   private UUID id;

    private String name;

    private  String description;

    private  BigDecimal price;

    private  Integer stock;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;
}
