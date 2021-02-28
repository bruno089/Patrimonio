package com.gabru.Patrimonio.business_controllers;

import com.gabru.Patrimonio.dtos.ConceptoDto;
import com.gabru.Patrimonio.entities.Concepto;
import com.gabru.Patrimonio.repositories.ConceptoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.Optional;

@Controller

public class ConceptoController {

    @Autowired
    ConceptoRepository conceptoRepository;

    public List<Concepto> buscarTodos(){
        List<Concepto> conceptosLis;
        conceptosLis = conceptoRepository.findAll();

        return conceptosLis;
    }

    public Optional<Concepto> buscar(int id){
        //todo cambiar con manejo de excepciones
        Optional<Concepto> con = conceptoRepository.findById(id);

        if (con.isPresent()){
            return con;
        }
        else{
            return null;
        }
    }

    public Concepto guardar(ConceptoDto conceptoDto){
        //todo Bru: manejo de excepciones
        Concepto concepto = new Concepto();
        concepto.setNombre(conceptoDto.getNombre());
        concepto.setIngreso(conceptoDto.isIngreso());

        return conceptoRepository.save(concepto);
    }

    public void borrar(int id){
        if (conceptoRepository.findById(id).isPresent()){
            conceptoRepository.deleteById(id);
        }
    }
}
