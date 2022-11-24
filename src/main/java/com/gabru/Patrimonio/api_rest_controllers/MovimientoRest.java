package com.gabru.Patrimonio.api_rest_controllers;

import com.gabru.Patrimonio.business_controllers.MovimientoController;
import com.gabru.Patrimonio.dtos.ArchivoDto;
import com.gabru.Patrimonio.dtos.MovimientoDto;
import com.gabru.Patrimonio.dtos.MovimientosTotalesPorConceptoDto;
import com.gabru.Patrimonio.entities.Movimiento;
import com.gabru.Patrimonio.exceptions.BadRequestException;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

@RestController
@RequestMapping(MovimientoRest.MOVIMIENTOS_ENDPOINT)
@AllArgsConstructor
public class MovimientoRest {
    public static final String MOVIMIENTOS_ENDPOINT = "/movimientos";
    public static final String MOVIMIENTO_ID = "/{movimientoId}";

    public static final String BUSQUEDA = "/busqueda";
    MovimientoController movimientoController;
    @PostMapping("/csv")
    public void registrarCsv ( @Valid ArchivoDto archivoDto, @RequestHeader(required = false) String tipoImportacion ){
        movimientoController.CsvAMovimientoDtoList(archivoDto.getArchivo());
    }
    @PostMapping
    public MovimientoDto agregar(@Valid @RequestBody MovimientoDto movimientoDto){
        return movimientoController.agregar(movimientoDto);
    }
    @DeleteMapping(MOVIMIENTO_ID)
    public void borrar(@PathVariable int movimientoId){
        movimientoController.borrar(movimientoId);
    }
    @PutMapping(MOVIMIENTO_ID)
    public MovimientoDto actualizar(@PathVariable Integer movimientoId, @Valid @RequestBody MovimientoDto movimientoDto){
        return  movimientoController.actualizar(movimientoId, movimientoDto);
    }
    @GetMapping("/busqueda/movimientoEntreFechas")
    public List<MovimientoDto> buscarMovimientosPorFecha(String fechaInicial, String fechaFinal){
        return movimientoController.buscarMovimientosPorFecha(fechaInicial, fechaFinal);
    }
    @GetMapping("/busqueda/totalizador")
    public List<MovimientosTotalesPorConceptoDto> totalizador( String fechaInicial, String fechaFinal){
        return movimientoController.totalizador(fechaInicial, fechaFinal);
    }
}
