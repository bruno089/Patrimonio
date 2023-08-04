package com.gabru.Patrimonio.api.resources;

import com.gabru.Patrimonio.domain.business_controllers.ConceptoController;
import com.gabru.Patrimonio.api.dtos.ConceptoDto;
import com.gabru.Patrimonio.data.entities.Concepto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@PreAuthorize("hasRole('ADMIN') or hasRole('OPERATOR')")
@RestController
@RequestMapping(ConceptoRest.CONCEPTOS_ENDPOINT)
@AllArgsConstructor
public class ConceptoRest {
    public static final String CONCEPTOS_ENDPOINT = "/conceptos";
    public static final String ID = "/{id}";
    public static final String CONCEPTO_NOMBRE = "/nombre";
    ConceptoController conceptoController;

    @PostMapping
    public ConceptoDto agregar(@Valid @RequestBody ConceptoDto conceptoDto){
        return conceptoController.agregar(conceptoDto);
    }

    @PutMapping(ID)
    public ConceptoDto actualizar(@PathVariable Integer id, @Valid @RequestBody ConceptoDto conceptoDto){
        return conceptoController.actualizar(id, conceptoDto);
    }
    @DeleteMapping(ID)
    public void borrar(@PathVariable int id){
        conceptoController.borrar(id);
    }
    @GetMapping(ID)
    public  ConceptoDto buscar(@PathVariable Integer id){
        return conceptoController.buscar(id);
    }

    @GetMapping
    @Operation(summary = "buscarTodos", security = @SecurityRequirement(name = "basicAuth"))
    public List<Concepto> buscarTodos(){
        return conceptoController.buscarTodos();
    }

    @GetMapping(CONCEPTO_NOMBRE) //Este tipo de busqueda la sintaxis no es la indicada
    public List<ConceptoDto> buscarPorNombre(@RequestParam String nombre){
        return conceptoController.buscarPorNombre(nombre);
    }
}
