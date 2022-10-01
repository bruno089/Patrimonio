package com.gabru.Patrimonio.dtos;

import lombok.Builder;
import lombok.Data;

@Data @Builder
public class MovimientosTotalesPorConceptoDto {
    String mes;
    String concepto;
    Double importe;
}
