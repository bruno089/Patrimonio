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
public class Movimiento {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;
    Double importe;
    String observacion;
    @Column(columnDefinition = "smalldatetime")
    LocalDate fecha;
    @Column(columnDefinition = "smalldatetime")
    LocalDateTime alta;
    @ManyToOne @JoinColumn(name = "nidConcepto")
    Concepto concepto;
}
