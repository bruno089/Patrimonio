package com.gabru.Patrimonio.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.gabru.Patrimonio.entities.Movimiento;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class MovimientoDto {
    Integer id;
    Double importe;
    String  observacion;
    String  fecha;
    Integer idConcepto;
    String  conceptoDescripcion;


    public MovimientoDto(Movimiento movimiento) {
        this.id = movimiento.getId();
        this.importe = movimiento.getImporte();
        this.observacion = movimiento.getObservacion();
        this.conceptoDescripcion = movimiento.getConcepto().getNombre();
    }
}
