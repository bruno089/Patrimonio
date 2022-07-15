package com.gabru.Patrimonio.api_rest_controllers;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(SeguridadRest.SEGURIDAD_ENDPOINT)
public class SeguridadRest {
    public static final String SEGURIDAD_ENDPOINT = "/seguridad";

    @GetMapping("/valorEncriptado")
    String devolverClaveEncriptada(String valor){
        return  new BCryptPasswordEncoder().encode(valor);
    }

}
