package com.gabru.Patrimonio.business_controllers;

import com.gabru.Patrimonio.entities.Concepto;
import com.gabru.Patrimonio.repositories.ConceptoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.ArrayList;
import java.util.List;

@Controller

public class ConceptoController {

    @Autowired
    ConceptoRepository conceptoRepository;

    public List<Concepto> findAll(){
        List<Concepto> conceptosLis;
        conceptosLis = conceptoRepository.findAll();
        return conceptosLis;
    }

    public Concepto save(){
        Concepto concepto = new Concepto();
        concepto.setNombre("Rodo");
        concepto.setIngreso(true);
        return conceptoRepository.save(concepto);
    }
}
