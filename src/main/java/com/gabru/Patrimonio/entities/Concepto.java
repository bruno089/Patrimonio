package com.gabru.Patrimonio.entities;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data @NoArgsConstructor @AllArgsConstructor @Builder
@Entity
@Table(name = "tpatconceptos")
public class Concepto{
    @Id  @Column(name = "nidconcepto") @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;
    @Column(name = "sconnombre")
    String nombre;
    @Column(name = "bconingreso")
    boolean ingreso;

  /*  public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public boolean isIngreso() {
        return ingreso;
    }

    public void setIngreso(boolean ingreso) {
        this.ingreso = ingreso;
    }*/
}


