package com.gabru.Patrimonio.business_controllers;

import com.gabru.Patrimonio.business_services.MailService;
import com.gabru.Patrimonio.dtos.UserDto;
import com.gabru.Patrimonio.entities.ConfirmationCode;
import com.gabru.Patrimonio.entities.Usuario;
import com.gabru.Patrimonio.exceptions.AlreadyExistException;
import com.gabru.Patrimonio.repositories.ConfirmationCodeRepository;
import com.gabru.Patrimonio.repositories.UsuarioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

class UsuarioControllerTest {
    @Mock   UsuarioRepository usuarioRepository;
    @Mock   ConfirmationCodeRepository confirmationCodeRepository;
    @Mock   MailService mailService;
    @InjectMocks    UsuarioController usuarioController;
    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);
        usuarioController =new UsuarioController (usuarioRepository,confirmationCodeRepository,mailService);
    }
    @Test
    void notNullTest(){
        assertThat(usuarioRepository).isNotNull();
        assertThat(confirmationCodeRepository).isNotNull();
        assertThat(mailService).isNotNull();
        assertThat(usuarioController).isNotNull();

    }
    @Test
    void registerEmailExistExcepcionTest () {

        String email = "j@minkatec.com";
        String nombre = "Jhon";

        UserDto userDto =   UserDto.builder().nombre(nombre).email(email).build();


       Optional<Usuario> usuarioMock =  Optional.of(Usuario.builder()
               .nombre("Jhon")
               .email(email).build());

        Mockito .when(usuarioRepository.findByEmailIgnoreCase(email))
                .thenReturn(usuarioMock);

        assertThrows(AlreadyExistException.class, ()-> usuarioController.registrar(userDto));
    }
    @Test
    void registerNombreExistExcepcionTest () {

        String email = "jhon@minkatec.com";
        String nombre = "Jhon";

        UserDto userDto =   UserDto.builder().nombre(nombre).email(email).build();

        Optional<Usuario> usuarioMock = Optional.of ( Usuario.builder().nombre(nombre).email(email).build() );

        Mockito .when(usuarioRepository.findByNombreIgnoreCase(Mockito.anyString()))
                .thenReturn(usuarioMock);

        assertThrows(AlreadyExistException.class, ()-> usuarioController.registrar(userDto));
    }
    @Test
    void registerOK (){

        String email = "jhon@minkatec.com";
        String nombre = "Jhon";
        String clave = "Test123";
        UserDto userDto =   UserDto.builder().nombre(nombre).email(email).password(clave).build();

        ConfirmationCode confirmationCode = new ConfirmationCode();
        confirmationCode.setCode("XCS2F1");

            Mockito
                    .when(confirmationCodeRepository.save(any()))
                    .thenReturn(confirmationCode);

        usuarioController.registrar(userDto);

        //2 verify ways. With and without "times"
        verify(usuarioRepository,times(1)).save(any());
        verify(confirmationCodeRepository).save(any());
        verify(mailService).sendMail(anyString(),anyString(),anyString());
    }


}