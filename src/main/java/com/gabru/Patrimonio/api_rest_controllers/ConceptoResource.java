package com.gabru.Patrimonio.api_rest_controllers;

import com.gabru.Patrimonio.business_controllers.ConceptoController;
import com.gabru.Patrimonio.entities.Concepto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(ConceptoResource.CONCEPTOS_ENDPOINT)
public class ConceptoResource {

    public static final String CONCEPTOS_ENDPOINT = "/conceptos";
    public static final String CONCEPTO_ID = "/{id}";

    @Autowired
    ConceptoController conceptoController;

    @GetMapping
    public List<Concepto> buscarTodosConOtroNombre(){
        return conceptoController.buscarTodos();
    }

    @GetMapping(CONCEPTO_ID)
    public Optional<Concepto> buscar(@PathVariable int id){
        return conceptoController.buscar(id);
    }

    @PostMapping
    public Concepto agregar(){
        return conceptoController.guardar();
    }

    @DeleteMapping
    public void borrar(){
        conceptoController.borrar();
    }
}
