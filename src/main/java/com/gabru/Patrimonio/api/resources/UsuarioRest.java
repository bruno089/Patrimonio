package com.gabru.Patrimonio.api.resources;

import com.gabru.Patrimonio.api.dtos.TokenOutputDto;
import com.gabru.Patrimonio.api.dtos.UserDto;
import com.gabru.Patrimonio.domain.business_controllers.UsuarioController;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(UsuarioRest.USERS)
@AllArgsConstructor
public class UsuarioRest {
    public static final String USERS = "/usuarios";
    public static final String LOGIN = "/login";
    public static final String CONFIRM_REGISTER = "/codigo-confirmacion";
    UsuarioController usuarioController;

    @PostMapping()
    public void registrar(@Valid @RequestBody UserDto userDto){
        usuarioController.registrar(userDto);
    }

    @GetMapping(CONFIRM_REGISTER)
    public void confirmacionCuenta ( @RequestParam() String confirmationCode){
        usuarioController.confirmacionCuenta(confirmationCode);
    }
    @PreAuthorize("authenticated")
    @PostMapping(LOGIN)
    public TokenOutputDto login( @AuthenticationPrincipal User activeUser) { //User class from spring security
        return usuarioController.login(activeUser.getUsername());
    }
}
