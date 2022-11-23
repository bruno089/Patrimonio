package com.gabru.Patrimonio.business_controllers;

import com.gabru.Patrimonio.dtos.ConceptoDto;
import com.gabru.Patrimonio.entities.Concepto;
import com.gabru.Patrimonio.exceptions.ConflictException;
import com.gabru.Patrimonio.exceptions.NotFoundException;
import com.gabru.Patrimonio.repositories.ConceptoRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@AllArgsConstructor
public class ConceptoController {
    ConceptoRepository conceptoRepository;
    public List<Concepto> buscarTodos(){
        List<Concepto> conceptosLis;
        conceptosLis = conceptoRepository.findAll();
        return conceptosLis;
    }

    public ConceptoDto buscar(int id){
        Optional<Concepto> con = conceptoRepository.findById(id);

        if (! con.isPresent()){
            throw new ConflictException("ConceptoId no existente: " + id );
        }

        return ConceptoDto.builder()
                            .id((con.get().getId()))
                            .nombre(con.get().getNombre())
                            .ingreso(con.get().isIngreso())
                            .build();
    }

    public ConceptoDto agregar(ConceptoDto conceptoDto){
        if (conceptoRepository.findByNombre(conceptoDto.getNombre()) .isPresent()){
            throw  new ConflictException("Concepto existente: " + conceptoDto.getNombre());
        }

        Concepto concepto =  Concepto.builder()
                .nombre(conceptoDto.getNombre())
                .ingreso(conceptoDto.isIngreso())
                .build();

        conceptoRepository.save(concepto);

        return ConceptoDto.builder()
                .nombre(concepto.getNombre())
                .ingreso(concepto.isIngreso())
                .build();
    }

    public void borrar(int id){
        if (conceptoRepository.findById(id).isPresent()){
            conceptoRepository.deleteById(id);
        }
    }

    public List<ConceptoDto> buscarPorNombre(String nombre){
        List<Concepto> conceptos;
        conceptos = conceptoRepository.findByNombreContaining(nombre);
        List<ConceptoDto> conceptoDtos = new ArrayList<>();

        conceptos.forEach( concepto -> { conceptoDtos.add(ConceptoDto.builder()
                .id(concepto.getId())
                .nombre(concepto.getNombre())
                .ingreso(concepto.isIngreso()).build());
        });

        if (conceptos.isEmpty()){
            throw new ConflictException("No hay elementos con: " + nombre );
        }
        return conceptoDtos;
    }

    public ConceptoDto actualizar(Integer conceptoId, ConceptoDto conceptoDto) {
        Concepto concepto = conceptoRepository.findById(conceptoId).orElseThrow(() -> new NotFoundException("No se encontró el concepto."));

        concepto.setNombre(conceptoDto.getNombre());
        concepto.setIngreso(conceptoDto.isIngreso());
        conceptoRepository.save(concepto);

        return new ConceptoDto(concepto);
    }
}
