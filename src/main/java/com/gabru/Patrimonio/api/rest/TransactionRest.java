package com.gabru.Patrimonio.api.rest;

import com.gabru.Patrimonio.api.dtos.ArchivoDto;
import com.gabru.Patrimonio.api.dtos.TransactionDto;
import com.gabru.Patrimonio.domain.services.TransactionService;
import com.gabru.Patrimonio.api.dtos.MovimientosTotalesPorConceptoDto;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
@PreAuthorize("hasRole('CUSTOMER')")
@RestController
@RequestMapping(TransactionRest.TRANSACTION)
@AllArgsConstructor
public class TransactionRest {
    public static final String TRANSACTION = "/transactions";
    public static final String TRANSACTION_ID = "/{transactionId}";

    public static final String SEARCH = "/search";
    TransactionService transactionService;

    /** CRUD **/
    @PostMapping
    public TransactionDto create ( @Valid @RequestBody TransactionDto transactionDto ){
        return transactionService.create(transactionDto);
    }
    @GetMapping(TRANSACTION_ID)
    public TransactionDto read ( @PathVariable Integer transactionId){
        return transactionService.read(transactionId);
    }
    @PutMapping(TRANSACTION_ID)
    public TransactionDto update ( @PathVariable Integer transactionId, @Valid @RequestBody TransactionDto transactionDto ){
        return  transactionService.update(transactionId, transactionDto);
    }
    @DeleteMapping(TRANSACTION_ID)
    public void delete ( @PathVariable int transactionId){
        transactionService.delete(transactionId);
    }

    @PostMapping("/csv")
    public void registrarCsv ( @Valid ArchivoDto archivoDto, @RequestHeader(required = false) String tipoImportacion ){
        transactionService.CsvAMovimientoDtoList(archivoDto.getArchivo(),tipoImportacion);
    }

    /** Searchs **/
    @GetMapping()
    public List<TransactionDto> readAll (){
        return transactionService.readAll();
    }
    @GetMapping(SEARCH + "/movimientoEntreFechas")
    public List<TransactionDto> readBetweenDates ( String fechaInicial, String fechaFinal){
        return transactionService.readBetweenDates(fechaInicial, fechaFinal);
    }
    @GetMapping(SEARCH + "/totalizador")
    public List<MovimientosTotalesPorConceptoDto> totalizador( String fechaInicial, String fechaFinal){
        return transactionService.totalizador(fechaInicial, fechaFinal);
    }
}
