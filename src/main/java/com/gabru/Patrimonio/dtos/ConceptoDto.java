package com.gabru.Patrimonio.dtos;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class ConceptoDto {
    @NotNull @Size(min = 2, max = 100 , message = "El tamaño es entre 2 y 100") @NotBlank(message = "Nombre es obligatorio")
    String nombre;
    @NotNull
    boolean ingreso;

    //todo Gab: Validar @NotNull en boolean en spring

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public boolean isIngreso() {
        return ingreso;
    }

    public void setIngreso(boolean ingreso) {
        this.ingreso = ingreso;
    }
}
