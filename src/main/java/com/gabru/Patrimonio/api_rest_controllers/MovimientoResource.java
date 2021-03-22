package com.gabru.Patrimonio.api_rest_controllers;

import com.gabru.Patrimonio.business_controllers.MovimientoController;
import com.gabru.Patrimonio.dtos.MovimientoDto;
import com.gabru.Patrimonio.entities.Movimiento;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping(MovimientoResource.MOVIMIENTOS_ENDPOINT)

public class MovimientoResource {
    public static final String MOVIMIENTOS_ENDPOINT = "/movimientos";

    @Autowired
    MovimientoController movimientoController;

    @PostMapping
    public Movimiento agregar(@Valid @RequestBody MovimientoDto movimientoDto){
        return movimientoController.guardar(movimientoDto);
    }
}
