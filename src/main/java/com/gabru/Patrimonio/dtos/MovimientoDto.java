package com.gabru.Patrimonio.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.gabru.Patrimonio.entities.Movimiento;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.format.DateTimeFormatter;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class MovimientoDto {
    Integer id;
    Double importe;
    String observacion;
    String fecha;
    Integer idConcepto;
    String conceptoDescripcion;
    Boolean conceptoIngreso;
    String alta;

    public MovimientoDto(Movimiento movimiento) {
        this.id = movimiento.getId();
        this.importe = movimiento.getImporte();
        this.observacion = movimiento.getObservacion();
        this.fecha = movimiento.getFecha().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        this.conceptoDescripcion = movimiento.getConcepto().getNombre();
        this.conceptoIngreso = movimiento.getConcepto().isIngreso();
        this.alta = movimiento.getAlta().toString();
    }
}
