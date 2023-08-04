package com.gabru.Patrimonio.data.entities;

import com.gabru.Patrimonio.utils.business_services.RandomGeneratorService;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
@Entity
@Data @NoArgsConstructor
public class ConfirmationCode {

    public static final int HOURS_EXPIRATION = 24;

    @Id    @GeneratedValue(strategy = GenerationType.AUTO)
    int id;
    @Column(unique = true)
    String code;
    LocalDateTime expiredDataToken;
    LocalDateTime createdDate;
    @OneToOne(targetEntity = Usuario.class, fetch = FetchType.EAGER)
    @JoinColumn(nullable = false, name = "id_usuario")
    Usuario usuario;

    public  ConfirmationCode(Usuario usuario){
        this.usuario = usuario;
        this.code = RandomGeneratorService.generateRandomString();
        this.createdDate = LocalDateTime.now();
        this.expiredDataToken =  LocalDateTime.now().plusHours(HOURS_EXPIRATION);
    }
}
