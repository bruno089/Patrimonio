package com.gabru.Patrimonio.api_rest_controllers;

import com.gabru.Patrimonio.business_controllers.MovimientoController;
import com.gabru.Patrimonio.dtos.MovimientoDto;
import com.gabru.Patrimonio.entities.Movimiento;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(MovimientoResource.MOVIMIENTOS_ENDPOINT)

public class MovimientoResource {
    public static final String MOVIMIENTOS_ENDPOINT = "/movimientos";
    public static final String MOVIMIENTO_ID = "/{movimientoId}";

    @Autowired
    MovimientoController movimientoController;

    @PostMapping
    public Movimiento agregar(@Valid @RequestBody MovimientoDto movimientoDto){
        return movimientoController.guardar(movimientoDto);
    }

    //Todo en base que vamos a borrar un movimiento:
    // cada movimiento deberia ser identificado inequivocamente.
    // Para eso debemos devolver el identificador en la respuesta de cuando se guarda
    @DeleteMapping(MOVIMIENTO_ID)
    public void borrar(@PathVariable int movimientoId){
        movimientoController.borrar(movimientoId);
    }

}
