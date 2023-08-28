package com.gabru.Patrimonio.data.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ResultCheckStyle;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data @NoArgsConstructor @AllArgsConstructor @Builder
@Entity()
@Table(name = "Movimiento")
@SQLDelete(sql = "UPDATE Movimiento  SET borrado = CURRENT_TIMESTAMP  where id = ?" , check = ResultCheckStyle.COUNT)
@Where(clause = "borrado IS NULL")
public class Transaction {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;
    Double importe;
    String observacion;
    @Column(columnDefinition = "smalldatetime")
    LocalDate fecha;
    @Column(columnDefinition = "smalldatetime")
    LocalDateTime alta;
    @Column(columnDefinition = "smalldatetime")
    LocalDateTime borrado;
    @ManyToOne @JoinColumn(name = "idconcepto")
    //@ManyToOne @JoinColumn(name = "id_category")
    Category category;

    @ManyToOne @JoinColumn(nullable = false, name = "id_usuario")
    Usuario usuario;


    /** Esto es para cuando se necesita consultar el estado borrado  en el mismo flujo del algoritmo.
    Ya que no se actualizo el valor borrado de este.
    Entonces se ejecutara  este codigo automaticamente para setear este valor en esos casos.
    No es necesario cuando se corta el flujo luego de borrar **/
    @PreRemove
    public void borrado(){
        this.borrado = LocalDateTime.now();
    }


}
