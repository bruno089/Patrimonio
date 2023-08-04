package com.gabru.Patrimonio.api.resources;

import com.gabru.Patrimonio.api.dtos.ArchivoDto;
import com.gabru.Patrimonio.api.dtos.TransactionDto;
import com.gabru.Patrimonio.domain.business_controllers.TransactionService;
import com.gabru.Patrimonio.api.dtos.MovimientosTotalesPorConceptoDto;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
@PreAuthorize("hasRole('ADMIN') or hasRole('OPERATOR')")
@RestController
@RequestMapping(TransactionResource.TRANSACTION)
@AllArgsConstructor
public class TransactionResource {
    public static final String TRANSACTION = "/movimientos";
    public static final String TRANSACTION_ID = "/{transactionId}";

    public static final String SEARCH = "/busqueda";
    TransactionService transactionService;
    @PostMapping("/csv")
    public void registrarCsv ( @Valid ArchivoDto archivoDto, @RequestHeader(required = false) String tipoImportacion ){
        transactionService.CsvAMovimientoDtoList(archivoDto.getArchivo(),tipoImportacion);
    }

    /** CRUD **/
    @PostMapping
    public TransactionDto agregar( @Valid @RequestBody TransactionDto transactionDto ){
        return transactionService.agregar(transactionDto);
    }
    @GetMapping(TRANSACTION_ID)
    public TransactionDto buscar( @PathVariable Integer transactionId){
        return transactionService.buscar(transactionId);
    }
    @PutMapping(TRANSACTION_ID)
    public TransactionDto actualizar( @PathVariable Integer transactionId, @Valid @RequestBody TransactionDto transactionDto ){
        return  transactionService.actualizar(transactionId, transactionDto);
    }
    @DeleteMapping(TRANSACTION_ID)
    public void borrar(@PathVariable int transactionId){
        transactionService.borrar(transactionId);
    }

    /** Searchs **/
    @GetMapping()
    public List<TransactionDto> buscarTodos(){
        return transactionService.buscarTodos();
    }
    @GetMapping("/busqueda/movimientoEntreFechas")
    public List<TransactionDto> buscarMovimientosPorFecha( String fechaInicial, String fechaFinal){
        return transactionService.buscarMovimientosPorFecha(fechaInicial, fechaFinal);
    }
    @GetMapping("/busqueda/totalizador")
    public List<MovimientosTotalesPorConceptoDto> totalizador( String fechaInicial, String fechaFinal){
        return transactionService.totalizador(fechaInicial, fechaFinal);
    }
}
