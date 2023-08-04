package com.gabru.Patrimonio.domain.business_controllers;

import com.gabru.Patrimonio.utils.business_services.JwtService;
import com.gabru.Patrimonio.utils.business_services.MailService;
import com.gabru.Patrimonio.api.dtos.TokenOutputDto;
import com.gabru.Patrimonio.api.dtos.UserDto;
import com.gabru.Patrimonio.data.entities.ConfirmationCode;
import com.gabru.Patrimonio.data.entities.Role;
import com.gabru.Patrimonio.data.entities.Usuario;
import com.gabru.Patrimonio.domain.exceptions.AlreadyExistException;
import com.gabru.Patrimonio.domain.exceptions.ConfirmationUserException;
import com.gabru.Patrimonio.domain.exceptions.NotFoundException;
import com.gabru.Patrimonio.data.repositories.ConfirmationCodeRepository;
import com.gabru.Patrimonio.data.repositories.UsuarioRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;

@Controller
@AllArgsConstructor
public class UsuarioController {
    UsuarioRepository usuarioRepository;
    ConfirmationCodeRepository confirmationCodeRepository;
    MailService mailService;
    JwtService jwtService;
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
                .roles(new Role[]{Role.CUSTOMER})
                .role(Role.CUSTOMER)
                .build();

         usuarioRepository.save(usuario);

        ConfirmationCode confirmationCode = confirmationCodeRepository.save(new ConfirmationCode(usuario));

        String host = "https://finanzas.brunolopezcross.com/";
        // String activeProfile = System.getProperty("spring.config.activate.on-profile");
        //if (! "docker".equals(activeProfile)) { host = "http://127.0.0.1:8080/"; }

        String endPointConfirmationCode = "usuarios/codigo-confirmacion?confirmationCode=";
        String urlCodeActivation = host + endPointConfirmationCode + confirmationCode.getCode();

        String message = "Welcome to Our App " + userDto.getNombre() +
                         " Please click the following link to activate your account: " + urlCodeActivation;

        mailService.sendMail(userDto.getEmail(), "Registration Code", message);
    }

    //Todo recuperarClave(String email)
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
    public TokenOutputDto login ( String username ) {

        Usuario user = usuarioRepository
                .findByNombreIgnoreCase(username)
                .orElseThrow(() -> new NotFoundException("Username not found: " + username));

        String[] roles = Arrays.stream(user.getRoles()).map(Role::name).toArray(String[]::new);

        return new TokenOutputDto(jwtService.createToken(user.getNombre(), user.getNombre(),roles));
    }

}