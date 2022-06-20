package com.gabru.Patrimonio.api_rest_controllers;

import com.gabru.Patrimonio.business_controllers.MovimientoController;
import com.gabru.Patrimonio.dtos.MovimientoDto;
import com.gabru.Patrimonio.entities.Movimiento;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(MovimientoRest.MOVIMIENTOS_ENDPOINT)

public class MovimientoRest {
    public static final String MOVIMIENTOS_ENDPOINT = "/movimientos";
    public static final String MOVIMIENTO_ID = "/{movimientoId}";

    public static final String BUSQUEDA = "/busqueda";
    @Autowired MovimientoController movimientoController;
    @PostMapping
    public MovimientoDto agregar(@Valid @RequestBody MovimientoDto movimientoDto){
        return movimientoController.guardar(movimientoDto);
    }
    @DeleteMapping(MOVIMIENTO_ID)
    public void borrar(@PathVariable int movimientoId){
        movimientoController.borrar(movimientoId);
    }
    @GetMapping("/busqueda/movimientoEntreFechas")
    public List<Movimiento> buscarMovimientosPorFecha(String fechaInicial, String fechaFinal){
        return movimientoController.buscarMovimientosPorFecha(fechaInicial, fechaFinal);
    }

    //Todo Leer y registrar Movimientos a traves de un Excel?

}
