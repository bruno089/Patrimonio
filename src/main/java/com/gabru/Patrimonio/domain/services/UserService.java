package com.gabru.Patrimonio.domain.services;

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
import com.gabru.Patrimonio.data.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;

@Service
@AllArgsConstructor
public class UserService {
    UserRepository userRepository;
    ConfirmationCodeRepository confirmationCodeRepository;
    MailService mailService;
    JwtService jwtService;
    @Transactional
    public void register ( UserDto userDto) {

        if( userRepository.findByEmailIgnoreCase(userDto.getEmail()).isPresent() ) {
            throw  new AlreadyExistException("The email: " + userDto.getEmail() + " Exist in app.");
        }
        if( userRepository.findByNombreIgnoreCase(userDto.getNombre()).isPresent() ) {
            throw  new AlreadyExistException("The username: " + userDto.getNombre() + " Exist in app.");
        }

        Usuario usuario= Usuario.builder()
                .nombre(userDto.getNombre())
                .clave(new BCryptPasswordEncoder().encode(userDto.getPassword()))
                .email(userDto.getEmail())
                .activo(false)
                .registro(LocalDate.now())
                .role(Role.CUSTOMER)
                .build();

         userRepository.save(usuario);

        ConfirmationCode confirmationCode = confirmationCodeRepository.save(new ConfirmationCode(usuario));

        String host = getEnvironmentHost();

        String endPointConfirmationCode = "usuarios/codigo-confirmacion?confirmationCode=";
        String urlCodeActivation = host + endPointConfirmationCode + confirmationCode.getCode();

        String message = "Welcome to Our App " + userDto.getNombre() +
                         " Please click the following link to activate your account: " + urlCodeActivation;

        mailService.sendMail(userDto.getEmail(), "Registration Code", message);
    }
    public String userConfirmation ( String code ) { //Todo si el codigo  ya fue usado el codigo sacarlo o borrarlo?

        if(code == null) {
            throw new ConfirmationUserException("Code confirmation null");
        }

        ConfirmationCode confirmationCode = confirmationCodeRepository
                .findByCode(code)
                .orElseThrow(()-> new NotFoundException("Not found code confirmation"));

        if ( LocalDateTime.now().isAfter(confirmationCode.getExpiredDataToken())){
            throw new ConfirmationUserException("Code confirmation expired: " + confirmationCode.getExpiredDataToken());
        }

        Usuario user = userRepository
                .findByEmailIgnoreCase(confirmationCode.getUsuario().getEmail())
                .orElseThrow(()-> new NotFoundException("Not found user email"));
        user.setActivo(true);
        userRepository.save(user);

        return "Account activated successfully, please login in the app.";
    }
    public TokenOutputDto login ( String username ) {

        Usuario user = userRepository
                .findByNombreIgnoreCase(username)
                .orElseThrow(() -> new NotFoundException("Username not found: " + username));

        String role = user.getRole().toString();
        String[] roles = {role};

        return new TokenOutputDto(jwtService.createToken(user.getNombre(), user.getNombre(),roles));
    }
    private String getEnvironmentHost() {
        String host = "https://finanzas.brunolopezcross.com/";
        /*String profile = System.getProperty("spring.config.activate.on-profile");
        if(profile == null  ) {
            host = "http://localhost:8080/";
        }*/
        return host;
    }

    /*void recuperarClave(String email) { //Todo recuperarClave(String email)
        //Todo generar un codigo de recuperacion de clave
        //Todo enviar un correo con el codigo de recuperacion de clave

    }
    //Todo recuperarClave(String email, String codigo)
    //Todo cambiarClave(String email, String clave)
    //Todo cambiarClave(String email, String codigo, String clave)*/
}
