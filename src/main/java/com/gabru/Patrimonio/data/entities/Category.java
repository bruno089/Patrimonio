package com.gabru.Patrimonio.data.entities;
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
@Table(name = "Category")
@SQLDelete(sql = "UPDATE Category SET deleted = CURRENT_TIMESTAMP  where id = ?" , check = ResultCheckStyle.COUNT)
@Where(clause = "deleted IS NULL")
public class Category {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;
    @Column(nullable = false,length = 50) //unique = true
    String name;
    @Column(columnDefinition = "smalldatetime")
    LocalDateTime deleted;

    @ManyToOne @JoinColumn(nullable = false, name = "id_usuario")
    Usuario user;

    @ManyToOne @JoinColumn(name = "id_category_group")
    CategoryGroup categoryGroup;
    @PreRemove
    public void deleted (){ this.deleted = LocalDateTime.now(); }
}


