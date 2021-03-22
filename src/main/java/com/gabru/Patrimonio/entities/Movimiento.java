package com.gabru.Patrimonio.entities;

import javax.persistence.*;
import java.time.LocalDate;

@Entity

@Table(name = "tPatPatrimonio")

public class Movimiento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    Double importe;
    String observacion;
    LocalDate fecha;
    @ManyToOne
    Concepto concepto;

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

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public Concepto getConcepto() {
        return concepto;
    }

    public void setConcepto(Concepto concepto) {
        this.concepto = concepto;
    }
}
