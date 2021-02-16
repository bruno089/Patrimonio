package com.gabru.Patrimonio.business_controllers;

import com.gabru.Patrimonio.entities.Concepto;
import com.gabru.Patrimonio.repositories.ConceptoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller

public class ConceptoController {
    @Autowired
    ConceptoRepository conceptoRepository;

    public String listar(){
        long total = conceptoRepository.count();
        //conceptoRepository.findAll();
        return String.valueOf("Total: " + total);
    }

    public Concepto agregar(){
        Concepto concepto = new Concepto();
        concepto.setNombre("Rodo");
        concepto.setIngreso(true);

        return conceptoRepository.save(concepto);
    }
}
