package com.gabru.Patrimonio.api.resources;

import com.gabru.Patrimonio.api.dtos.TokenOutputDto;
import com.gabru.Patrimonio.api.dtos.UserDto;
import com.gabru.Patrimonio.domain.services.UserService;
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
    UserService userService;

    @PostMapping()
    public void registrar(@Valid @RequestBody UserDto userDto){
        userService.registrar(userDto);
    }

    @GetMapping(CONFIRM_REGISTER)
    public void confirmacionCuenta ( @RequestParam() String confirmationCode){
        userService.confirmacionCuenta(confirmationCode);
    }
    @PreAuthorize("authenticated")
    @PostMapping(LOGIN)
    public TokenOutputDto login( @AuthenticationPrincipal User activeUser) { //User class from spring security
        return userService.login(activeUser.getUsername());
    }
}
