package com.gabru.Patrimonio.dtos;

import com.gabru.Patrimonio.entities.Concepto;

import javax.persistence.ManyToOne;
import java.time.LocalDate;

public class MovimientoDto {
    Double importe;
    String observacion;
    String fecha;
    int conceptoId;

    public Double getImporte() {
        return importe;
    }

    public void setImporte(Double importe) {
        this.importe = importe;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public int getConceptoId() {
        return conceptoId;
    }

    public void setConceptoId(int conceptoId) {
        this.conceptoId = conceptoId;
    }
}
