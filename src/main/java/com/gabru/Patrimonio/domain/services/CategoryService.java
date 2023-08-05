package com.gabru.Patrimonio.domain.services;

import com.gabru.Patrimonio.api.dtos.CategoryDto;
import com.gabru.Patrimonio.data.entities.Category;
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

        Category category =  Category.builder()
                .nombre(categoryDto.getNombre())
                .ingreso(categoryDto.isIngreso())
                .build();

        categoryRepository.save(category);
        return new CategoryDto(category);
    }
    public CategoryDto read ( int id){
        Optional<Category> con = categoryRepository.findById(id);

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
        Category category = categoryRepository.findById(conceptoId).orElseThrow(() -> new NotFoundException("No se encontró el concepto."));

        category.setNombre(categoryDto.getNombre());
        category.setIngreso(categoryDto.isIngreso());
        categoryRepository.save(category);

        return new CategoryDto(category);
    }
    public void delete ( int id){
        if ( categoryRepository.findById(id).isPresent()){
            categoryRepository.deleteById(id);
        }
    }

    //Search Section
    public List<Category> buscarTodos(){
        List<Category> categories; //Refactor for CategoryDto[] using mapstruct
        categories = categoryRepository.findAll();
        return categories;
    }
    public List<CategoryDto> buscarPorNombre( String nombre){
        List<Category> categories;
        categories = categoryRepository.findByNombreContaining(nombre);
        List<CategoryDto> categoryDtos = new ArrayList<>();

        categories.forEach(concepto -> { categoryDtos.add(CategoryDto.builder()
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
    public Category getConcepto( String conceptoDescripcion){ //Todo try catch?
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

        Map<String, Category> conceptosEnBDMap =  new HashMap<>();
        categoryRepository
                .findAll()
                .forEach( concepto -> conceptosEnBDMap.put(concepto.getNombre().toUpperCase(),concepto) );

        Category categoryResultado;

        if ( conceptosEnBDMap.containsKey(conceptoDescripcionUpper) ){
            categoryResultado = conceptosEnBDMap.get(conceptoDescripcionUpper);
        }else{
            categoryResultado =  categoryRepository.save(
                    Category.builder().nombre(conceptoDescripcion).ingreso(CONCEPTO_TIPO_DEFAULT).build());
        }

        return categoryResultado;
    }
}
