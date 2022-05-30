package com.gabru.Patrimonio.dtos;

import lombok.Data;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
@Data
public class ConceptoDto {
    @NotNull @Size(min = 2, max = 100 , message = "El tamaño es entre 2 y 100") @NotBlank(message = "Nombre es obligatorio")
    String nombre;
    @NotNull
    boolean ingreso;
}
