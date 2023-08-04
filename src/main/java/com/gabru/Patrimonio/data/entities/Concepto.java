package com.gabru.Patrimonio.entities;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ResultCheckStyle;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data @NoArgsConstructor @AllArgsConstructor @Builder
@Entity
@SQLDelete(sql = "UPDATE Concepto SET borrado = CURRENT_TIMESTAMP  where id = ?" , check = ResultCheckStyle.COUNT)
@Where(clause = "borrado IS NULL")
public class Concepto {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;
    String nombre;
    boolean ingreso;
    @Column(columnDefinition = "smalldatetime")
    LocalDateTime borrado;
    @PreRemove
    public void borrado(){
        this.borrado = LocalDateTime.now();
    }
}


