package com.gabru.Patrimonio.entities;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data @NoArgsConstructor @AllArgsConstructor @Builder
@Entity
public class Concepto {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    String nombre;

    boolean ingreso;
}


