package com.gabru.Patrimonio.api_rest_controllers;

import com.gabru.Patrimonio.business_controllers.ConceptoController;
import com.gabru.Patrimonio.dtos.ConceptoDto;
import com.gabru.Patrimonio.entities.Concepto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


@RestController
@RequestMapping(ConceptoRest.CONCEPTOS_ENDPOINT)
@SecurityRequirement(name = "javainuseapi")
@AllArgsConstructor
public class ConceptoRest {
    public static final String CONCEPTOS_ENDPOINT = "/conceptos";
    public static final String CONCEPTO_ID = "/id";
    public static final String CONCEPTO_ID_REQUEST_PARAM = "/{id}";
    public static final String CONCEPTO_NOMBRE = "/nombre";
    public static final String CONCEPTO_FILTRO = "/filtro/{filtro}";
    ConceptoController conceptoController;

    @GetMapping
    @Operation(summary = "buscarTodos", security = @SecurityRequirement(name = "basicAuth"))
    public List<Concepto> buscarTodos(){
        return conceptoController.buscarTodos();
    }
    @GetMapping(CONCEPTO_ID)
    public  ConceptoDto buscar(@RequestParam int id){
        return conceptoController.buscar(id);
    }

    @GetMapping(CONCEPTO_NOMBRE)
    public List<ConceptoDto> buscarPorNombre(@RequestParam String nombre){
        return conceptoController.buscarPorNombre(nombre);
    }

    @PostMapping
    public ConceptoDto agregar(@Valid @RequestBody ConceptoDto conceptoDto){
        return conceptoController.agregar(conceptoDto);
    }

    @PutMapping( CONCEPTO_ID_REQUEST_PARAM)
    public ConceptoDto actualizar(@PathVariable Integer id, @Valid @RequestBody ConceptoDto conceptoDto){
        return conceptoController.actualizar(id, conceptoDto);
    }

    @DeleteMapping(CONCEPTO_ID)
    public void borrar(@PathVariable int id){
        conceptoController.borrar(id);
    } //Todo no permite borrar si ya tiene conceptos asociados¿?
}
