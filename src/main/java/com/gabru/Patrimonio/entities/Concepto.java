package com.gabru.Patrimonio.entities;
import javax.persistence.*;

@Entity
//@Table(name = "Concepto")
public class Concepto{
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;
    String nombre;
    boolean ingreso;

    public int getId() {
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
    }
}


