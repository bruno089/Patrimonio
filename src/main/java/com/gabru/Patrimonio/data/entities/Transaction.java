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
@Entity
@Table(name = "[Transaction]")
@SQLDelete(sql = "UPDATE Transaction  SET deleted = CURRENT_TIMESTAMP  where id = ?" , check = ResultCheckStyle.COUNT)
@Where(clause = "deleted IS NULL")
public class Transaction {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;
    Double amount;

    String detail;
    @Column(columnDefinition = "smalldatetime")
    LocalDate date;
    @Column(columnDefinition = "smalldatetime")
    LocalDateTime dateCreation;
    @Column(columnDefinition = "smalldatetime")
    LocalDateTime deleted;

    @ManyToOne @JoinColumn(name = "id_category")
    Category category;

    @ManyToOne @JoinColumn(nullable = false, name = "id_user")
    Usuario user;


    /** Esto es para cuando se necesita consultar el estado borrado  en el mismo flujo del algoritmo.
    Ya que no se actualizo el valor borrado de este.
    Entonces se ejecutara  este codigo automaticamente para setear este valor en esos casos.
    No es necesario cuando se corta el flujo luego de borrar **/
    @PreRemove
    public void deleted (){
        this.deleted = LocalDateTime.now();
    }


}
