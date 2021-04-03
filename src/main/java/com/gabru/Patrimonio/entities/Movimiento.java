package com.gabru.Patrimonio.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data @NoArgsConstructor @AllArgsConstructor @Builder
@Entity
@Table(name = "tpatpatrimonio")
public class Movimiento {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) @Column(name = "nidpatrimonio")
    int id;
    @Column(name = "npatimporte")
    Double importe;
    @Column(name = "spatobservacion")
    String observacion;
    @Column(name = "dpatfecha",columnDefinition = "smalldatetime")
    LocalDate fecha;
    @Column(name = "dpatalta",columnDefinition = "smalldatetime")
    LocalDateTime alta;
    @ManyToOne @JoinColumn(name = "nidconcepto")
    Concepto concepto;

  /*  public Double getImporte() {
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

    public LocalDateTime getAlta() {
        return alta;
    }

    public void setAlta(LocalDateTime alta) {
        this.alta = alta;
    }*/
}
