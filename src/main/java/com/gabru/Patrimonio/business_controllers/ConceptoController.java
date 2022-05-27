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

    @Autowired    ConceptoRepository conceptoRepository;

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

    public ConceptoDto guardar(ConceptoDto conceptoDto){

        Optional<Concepto> optionalConcepto  = conceptoRepository.findByNombre(conceptoDto.getNombre());
        if (optionalConcepto.isPresent()){
            //rebota y que no continue el proceso de insercion
            return null; //todo Bru: manejo de excepciones
        }else{
            Concepto concepto = new Concepto();
            concepto.setNombre(conceptoDto.getNombre());
            concepto.setIngreso(conceptoDto.isIngreso());

            concepto = conceptoRepository.save(concepto);

            ConceptoDto conceptoDtoSalida = new ConceptoDto();
            conceptoDtoSalida.setNombre("El nombre es: " + concepto.getNombre());
            conceptoDtoSalida.setIngreso(concepto.isIngreso());

            return conceptoDtoSalida;

        }
    }

    public void borrar(int id){
        if (conceptoRepository.findById(id).isPresent()){
            conceptoRepository.deleteById(id);
        }
    }
}
