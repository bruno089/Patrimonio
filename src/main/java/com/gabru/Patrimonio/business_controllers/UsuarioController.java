package com.gabru.Patrimonio.business_controllers;

import com.gabru.Patrimonio.business_services.MailService;
import com.gabru.Patrimonio.dtos.UserDto;
import com.gabru.Patrimonio.entities.ConfirmationCode;
import com.gabru.Patrimonio.entities.Role;
import com.gabru.Patrimonio.entities.Usuario;
import com.gabru.Patrimonio.exceptions.AlreadyExistException;
import com.gabru.Patrimonio.exceptions.ConfirmationUserException;
import com.gabru.Patrimonio.exceptions.NotFoundException;
import com.gabru.Patrimonio.repositories.ConfirmationCodeRepository;
import com.gabru.Patrimonio.repositories.UsuarioRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Controller
@AllArgsConstructor
public class UsuarioController {
    UsuarioRepository usuarioRepository;
    ConfirmationCodeRepository confirmationCodeRepository;
    MailService mailService;
    @Transactional
    public void registrar(UserDto userDto) {

        if( usuarioRepository.findByEmailIgnoreCase(userDto.getEmail()).isPresent() ) {
            throw  new AlreadyExistException("The email: " + userDto.getEmail() + " Exist in app.");
        }
        if( usuarioRepository.findByNombreIgnoreCase(userDto.getNombre()).isPresent() ) {
            throw  new AlreadyExistException("The username: " + userDto.getNombre() + " Exist in app.");
        }

        Usuario usuario= Usuario.builder()
                .nombre(userDto.getNombre())
                .clave(new BCryptPasswordEncoder().encode(userDto.getPassword()))
                .email(userDto.getEmail())
                .activo(false)
                .registro(LocalDate.now())
                .roles(new Role[]{Role.AUTHENTICATED})
                .build();
         usuarioRepository.save(usuario);

        ConfirmationCode confirmationCode = confirmationCodeRepository.save(new ConfirmationCode(usuario));

        mailService.sendMail(userDto.getEmail(),"Registration Code","Your activation code is: " + confirmationCode.getCode());
    }
    public void confirmacionCuenta ( String code ) {

        if(code == null) {
            throw new ConfirmationUserException("Code confirmation null");
        }

        ConfirmationCode confirmationCode = confirmationCodeRepository
                .findByCode(code)
                .orElseThrow(()-> new NotFoundException("Not found code confirmation"));

        if ( LocalDateTime.now().isAfter(confirmationCode.getExpiredDataToken())){
            throw new ConfirmationUserException("Code confirmation expired: " + confirmationCode.getExpiredDataToken());
        }

        Usuario user = usuarioRepository
                .findByEmailIgnoreCase(confirmationCode.getUsuario().getEmail())
                .orElseThrow(()-> new NotFoundException("Not found user email"));
        user.setActivo(true);
        usuarioRepository.save(user);
    }
}
