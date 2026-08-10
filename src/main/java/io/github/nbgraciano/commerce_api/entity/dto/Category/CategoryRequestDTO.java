package io.github.nbgraciano.commerce_api.entity.dto.Category;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CategoryRequestDTO(

        @NotBlank(message = "Name obligatory")
        @Size(min=2,max=100,message = "The name must be no more than 100 characters")
        String name
) {
}
