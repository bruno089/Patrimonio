package com.gabru.Patrimonio.api_rest_controllers;

import com.gabru.Patrimonio.business_controllers.UsuarioController;
import com.gabru.Patrimonio.dtos.UserDto;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(UsuarioRest.USERS)
@AllArgsConstructor
public class UsuarioRest {
    public static final String USERS = "/usuarios";
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
}
