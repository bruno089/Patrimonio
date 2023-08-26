package com.gabru.Patrimonio.data.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ResultCheckStyle;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;

@Data@NoArgsConstructor@AllArgsConstructor@Builder
@Entity
@Table(name = "CategoryGroup")
//Todo for delete
//@SQLDelete(sql = "UPDATE Concepto SET borrado = CURRENT_TIMESTAMP  where id = ?" , check = ResultCheckStyle.COUNT)
//@Where(clause = "delete IS NULL")
public class CategoryGroup {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;
    @Column(unique = true , length = 50 )
    String name;
    @ManyToOne @JoinColumn(nullable = false, name = "id_usuario")
    Usuario usuario;

}
