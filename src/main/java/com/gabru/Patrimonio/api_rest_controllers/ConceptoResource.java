package com.gabru.Patrimonio.api_rest_controllers;

import com.gabru.Patrimonio.business_controllers.ConceptoController;
import com.gabru.Patrimonio.entities.Concepto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/conceptos")
public class ConceptoResource {
    @Autowired
    ConceptoController conceptoController;

    @GetMapping
    public List<Concepto> findAll(){
        return conceptoController.findAll();
    }

    @PostMapping
    public Concepto agregar(){
        return conceptoController.save();
    }
}
