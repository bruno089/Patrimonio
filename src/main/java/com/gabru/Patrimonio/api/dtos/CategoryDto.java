package com.gabru.Patrimonio.api.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.gabru.Patrimonio.data.entities.Concepto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
@Data @NoArgsConstructor
@AllArgsConstructor @Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CategoryDto {
    int id;
    @NotNull @Size(min = 2, max = 100 , message = "El tamaño es entre 2 y 100") @NotBlank(message = "Nombre es obligatorio")
    String nombre;
    @NotNull
    boolean ingreso;

    public CategoryDto ( Concepto concepto){
        this.id = concepto.getId();
        this.nombre = concepto.getNombre();
        this.ingreso = concepto.isIngreso();
    }
}

