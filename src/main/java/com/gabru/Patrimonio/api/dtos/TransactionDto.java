package com.gabru.Patrimonio.api.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.gabru.Patrimonio.data.entities.Transaction;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.format.DateTimeFormatter;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class TransactionDto {
    Integer id;
    Double importe;
    String observacion;
    String fecha;
    Integer idConcepto;
    String conceptoDescripcion;
    Boolean conceptoIngreso;
    String alta;

    public TransactionDto ( Transaction transaction ) {
        this.id = transaction.getId();
        this.importe = transaction.getImporte();
        this.observacion = transaction.getObservacion();
        this.fecha = transaction.getFecha().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        this.conceptoDescripcion = transaction.getCategory().getNombre();
     //   this.conceptoIngreso = transaction.getCategory().isIngreso();
        this.alta = transaction.getAlta().toString();
    }
}
