package com.gabru.Patrimonio.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Data @Builder @NoArgsConstructor @AllArgsConstructor
@Entity
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;
    @Column( unique = true, nullable = false, length = 100)
    String nombre;
    @Column(nullable = false, length = 100)
    String clave;
    @Column(length = 100)
    String email;
    boolean activo;
    LocalDate registro;

    @Transient
    Role[] roles = new Role[]{Role.AUTHENTICATED};
}
