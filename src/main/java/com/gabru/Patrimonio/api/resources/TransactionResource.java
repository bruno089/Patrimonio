package com.gabru.Patrimonio.api.rest;

import com.gabru.Patrimonio.api.dtos.ArchivoDto;
import com.gabru.Patrimonio.api.dtos.TransactionDto;
import com.gabru.Patrimonio.domain.business_controllers.MovimientoController;
import com.gabru.Patrimonio.api.dtos.MovimientosTotalesPorConceptoDto;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
@PreAuthorize("hasRole('ADMIN') or hasRole('OPERATOR')")
@RestController
@RequestMapping(TransactionRest.TRANSACTION_RESOURCE)
@AllArgsConstructor
public class TransactionRest {
    public static final String TRANSACTION_RESOURCE = "/movimientos";
    public static final String MOVIMIENTO_ID = "/{movimientoId}";

    public static final String BUSQUEDA = "/busqueda";
    MovimientoController movimientoController;
    @PostMapping("/csv")
    public void registrarCsv ( @Valid ArchivoDto archivoDto, @RequestHeader(required = false) String tipoImportacion ){
        movimientoController.CsvAMovimientoDtoList(archivoDto.getArchivo(),tipoImportacion);
    }

    /** CRUD **/
    @PostMapping
    public TransactionDto agregar( @Valid @RequestBody TransactionDto transactionDto ){
        return movimientoController.agregar(transactionDto);
    }
    @GetMapping(MOVIMIENTO_ID)
    public TransactionDto buscar( @PathVariable Integer movimientoId){
        return movimientoController.buscar(movimientoId);
    }
    @PutMapping(MOVIMIENTO_ID)
    public TransactionDto actualizar( @PathVariable Integer movimientoId, @Valid @RequestBody TransactionDto transactionDto ){
        return  movimientoController.actualizar(movimientoId, transactionDto);
    }
    @DeleteMapping(MOVIMIENTO_ID)
    public void borrar(@PathVariable int movimientoId){
        movimientoController.borrar(movimientoId);
    }

    /** Searchs **/
    @GetMapping()
    public List<TransactionDto> buscarTodos(){
        return movimientoController.buscarTodos();
    }
    @GetMapping("/busqueda/movimientoEntreFechas")
    public List<TransactionDto> buscarMovimientosPorFecha( String fechaInicial, String fechaFinal){
        return movimientoController.buscarMovimientosPorFecha(fechaInicial, fechaFinal);
    }
    @GetMapping("/busqueda/totalizador")
    public List<MovimientosTotalesPorConceptoDto> totalizador( String fechaInicial, String fechaFinal){
        return movimientoController.totalizador(fechaInicial, fechaFinal);
    }
}
