package com.gabru.Patrimonio.domain.services;

import com.gabru.Patrimonio.api.dtos.ConceptoDto;
import com.gabru.Patrimonio.data.entities.Concepto;
import com.gabru.Patrimonio.domain.exceptions.ConflictException;
import com.gabru.Patrimonio.domain.exceptions.NotFoundException;
import com.gabru.Patrimonio.data.repositories.ConceptoRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@AllArgsConstructor
public class ConceptoService {
    public static final boolean CONCEPTO_TIPO_DEFAULT = false;
    ConceptoRepository conceptoRepository;

    public ConceptoDto agregar(ConceptoDto conceptoDto){
        if (conceptoRepository.findByNombre(conceptoDto.getNombre()) .isPresent()){
            throw new ConflictException("Concepto existente: " + conceptoDto.getNombre());
        }

        Concepto concepto =  Concepto.builder()
                .nombre(conceptoDto.getNombre())
                .ingreso(conceptoDto.isIngreso())
                .build();

        conceptoRepository.save(concepto);
        return new ConceptoDto(concepto);
    }

    public void borrar(int id){
        if (conceptoRepository.findById(id).isPresent()){
            conceptoRepository.deleteById(id);
        }
    }

    public ConceptoDto actualizar(Integer conceptoId, ConceptoDto conceptoDto) {
        Concepto concepto = conceptoRepository.findById(conceptoId).orElseThrow(() -> new NotFoundException("No se encontró el concepto."));

        concepto.setNombre(conceptoDto.getNombre());
        concepto.setIngreso(conceptoDto.isIngreso());
        conceptoRepository.save(concepto);

        return new ConceptoDto(concepto);
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

    public List<Concepto> buscarTodos(){
        List<Concepto> conceptosLis;
        conceptosLis = conceptoRepository.findAll();
        return conceptosLis;
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

        //TODO PREGUNTAR SI SE PUEDE NO USAR ESTA FORMA
        //if (conceptos.isEmpty()){
         //   throw new ConflictException("No hay elementos con: " + nombre );
        //}
        return conceptoDtos;
    }
    public Concepto getConcepto( String conceptoDescripcion){ //Todo try catch?
        /** Concepto  - Servicio
         *
         * El manejo de concepto tiene q estar nucleado en un solo lugar (Principio de Unica Responsabilidad)
         * El servicio se debe de encargar de devolver el concepto en base a su descripcion. Debe poder distinguir entre minusculas y mayusculas Comida COMIDA
         *          *  Ademas si el concepto no existe en BD se debe guardar este concepto
         * Cuidado:
         * - Case sensitive                 --
         * - En plural y/o en singular      xx
         * - Con muchas llamadas a BD       --
         * *
         * */

        //Limpiezas del Concepto, segun se vayan necesitando
        conceptoDescripcion = conceptoDescripcion.trim();
        String conceptoDescripcionUpper = conceptoDescripcion.toUpperCase();

        Map<String,Concepto> conceptosEnBDMap =  new HashMap<>();
        conceptoRepository
                .findAll()
                .forEach( concepto -> conceptosEnBDMap.put(concepto.getNombre().toUpperCase(),concepto) );

        Concepto conceptoResultado;

        if ( conceptosEnBDMap.containsKey(conceptoDescripcionUpper) ){
            conceptoResultado = conceptosEnBDMap.get(conceptoDescripcionUpper);
        }else{
            conceptoResultado =  conceptoRepository.save(
                    Concepto.builder().nombre(conceptoDescripcion).ingreso(CONCEPTO_TIPO_DEFAULT).build());
        }

        return conceptoResultado;
    }

}
