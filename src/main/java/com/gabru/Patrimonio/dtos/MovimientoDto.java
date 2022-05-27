package com.gabru.Patrimonio.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.gabru.Patrimonio.entities.Movimiento;
import lombok.Data;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class MovimientoDto {
    Integer id;
    Double importe;
    String observacion;
    String fecha;
    int conceptoId;
    String concepto;

    public MovimientoDto(Movimiento movimiento) {
        this.id = movimiento.getId();
        this.importe = movimiento.getImporte();
        this.observacion = movimiento.getObservacion();
        this.concepto = movimiento.getConcepto().getNombre();
    }
}