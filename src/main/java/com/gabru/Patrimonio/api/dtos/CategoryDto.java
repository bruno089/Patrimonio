package com.gabru.Patrimonio.api.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.gabru.Patrimonio.data.entities.Category;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Data @NoArgsConstructor
@AllArgsConstructor @Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CategoryDto {
    int id;
    @NotNull @Size(min = 2, max = 100 , message = "El tamaño es entre 2 y 100") @NotBlank(message = "Nombre es obligatorio")
    String nombre;
    LocalDateTime borrado;
    public CategoryDto ( Category category ){
        this.id = category.getId();
        this.nombre = category.getName();
        this.borrado = category.getDeleted();
    }
}

