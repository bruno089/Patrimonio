package com.gabru.Patrimonio.domain.services;

import com.gabru.Patrimonio.api.dtos.CategoryDto;
import com.gabru.Patrimonio.data.entities.Concepto;
import com.gabru.Patrimonio.domain.exceptions.ConflictException;
import com.gabru.Patrimonio.domain.exceptions.NotFoundException;
import com.gabru.Patrimonio.data.repositories.CategoryRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@AllArgsConstructor
public class CategoryService {
    public static final boolean CONCEPTO_TIPO_DEFAULT = false;
    CategoryRepository categoryRepository;
    public CategoryDto create ( CategoryDto categoryDto ){
        if ( categoryRepository.findByNombre(categoryDto.getNombre()) .isPresent()){
            throw new ConflictException("Concepto existente: " + categoryDto.getNombre());
        }

        Concepto concepto =  Concepto.builder()
                .nombre(categoryDto.getNombre())
                .ingreso(categoryDto.isIngreso())
                .build();

        categoryRepository.save(concepto);
        return new CategoryDto(concepto);
    }
    public CategoryDto read ( int id){
        Optional<Concepto> con = categoryRepository.findById(id);

        if (! con.isPresent()){
            throw new ConflictException("ConceptoId no existente: " + id );
        }

        return CategoryDto.builder()
                .id((con.get().getId()))
                .nombre(con.get().getNombre())
                .ingreso(con.get().isIngreso())
                .build();
    }
    public CategoryDto update ( Integer conceptoId, CategoryDto categoryDto ) {
        Concepto concepto = categoryRepository.findById(conceptoId).orElseThrow(() -> new NotFoundException("No se encontró el concepto."));

        concepto.setNombre(categoryDto.getNombre());
        concepto.setIngreso(categoryDto.isIngreso());
        categoryRepository.save(concepto);

        return new CategoryDto(concepto);
    }
    public void delete ( int id){
        if ( categoryRepository.findById(id).isPresent()){
            categoryRepository.deleteById(id);
        }
    }

    //Search Section
    public List<Concepto> buscarTodos(){
        List<Concepto> conceptosLis;
        conceptosLis = categoryRepository.findAll();
        return conceptosLis;
    }
    public List<CategoryDto> buscarPorNombre( String nombre){
        List<Concepto> conceptos;
        conceptos = categoryRepository.findByNombreContaining(nombre);
        List<CategoryDto> categoryDtos = new ArrayList<>();

        conceptos.forEach( concepto -> { categoryDtos.add(CategoryDto.builder()
                .id(concepto.getId())
                .nombre(concepto.getNombre())
                .ingreso(concepto.isIngreso()).build());
        });

        //TODO PREGUNTAR SI SE PUEDE NO USAR ESTA FORMA
        //if (conceptos.isEmpty()){
         //   throw new ConflictException("No hay elementos con: " + nombre );
        //}
        return categoryDtos;
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
        categoryRepository
                .findAll()
                .forEach( concepto -> conceptosEnBDMap.put(concepto.getNombre().toUpperCase(),concepto) );

        Concepto conceptoResultado;

        if ( conceptosEnBDMap.containsKey(conceptoDescripcionUpper) ){
            conceptoResultado = conceptosEnBDMap.get(conceptoDescripcionUpper);
        }else{
            conceptoResultado =  categoryRepository.save(
                    Concepto.builder().nombre(conceptoDescripcion).ingreso(CONCEPTO_TIPO_DEFAULT).build());
        }

        return conceptoResultado;
    }
}
