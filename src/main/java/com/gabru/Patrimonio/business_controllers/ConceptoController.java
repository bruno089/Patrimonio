package com.gabru.Patrimonio.business_controllers;

import com.gabru.Patrimonio.dtos.ConceptoDto;
import com.gabru.Patrimonio.entities.Concepto;
import com.gabru.Patrimonio.exceptions.ConflictException;
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
        Optional<Concepto> con = conceptoRepository.findById(id);
        if (! con.isPresent()){
            throw new ConflictException("ConceptoId no existente: " + id ); // Cambiado  con manejo de excepciones
        }
        return con;
    }

    public ConceptoDto guardar(ConceptoDto conceptoDto){

        if (conceptoRepository.findByNombre(conceptoDto.getNombre()) .isPresent()){
            throw  new ConflictException("Concepto existente: " + conceptoDto.getNombre());
        }

        Concepto concepto = new Concepto();
        concepto.setNombre(conceptoDto.getNombre());
        concepto.setIngreso(conceptoDto.isIngreso());
        conceptoRepository.save(concepto);

        ConceptoDto conceptoDtoSalida = new ConceptoDto();
        conceptoDtoSalida.setNombre("El nombre es: " + concepto.getNombre());
        conceptoDtoSalida.setIngreso(concepto.isIngreso());

        return conceptoDtoSalida;
    }

    public void borrar(int id){
        if (conceptoRepository.findById(id).isPresent()){
            conceptoRepository.deleteById(id);
        }
    }
}
