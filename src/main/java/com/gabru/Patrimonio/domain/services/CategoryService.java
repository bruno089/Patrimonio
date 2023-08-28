package com.gabru.Patrimonio.domain.services;

import com.gabru.Patrimonio.api.dtos.CategoryDto;
import com.gabru.Patrimonio.data.entities.Category;
import com.gabru.Patrimonio.data.entities.Usuario;
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
    UserDetailsServiceImpl userDetailsServiceImpl;
    public CategoryDto create ( CategoryDto categoryDto ){

        if ( categoryRepository.findByNameAndUser(categoryDto.getNombre(),userDetailsServiceImpl.getUsuarioAutenticado()).isPresent()){
            throw new ConflictException("Already exist Category: " + categoryDto.getNombre());
        }

        Category category =  Category.builder()
                .name(categoryDto.getNombre())
                //.ingreso(categoryDto.isIngreso())
                .user(userDetailsServiceImpl.getUsuarioAutenticado())
                .build();

        categoryRepository.save(category);
        return new CategoryDto(category);
    }
    public CategoryDto read ( int id){
        Optional<Category> categoryOptional = categoryRepository.findByIdAndUser(id,userDetailsServiceImpl.getUsuarioAutenticado());

        if (! categoryOptional.isPresent()){
            throw new NotFoundException("Not found category ID: " + id );
        }

        return CategoryDto.builder()
                .id((categoryOptional.get().getId()))
                .nombre(categoryOptional.get().getName())
                .build();
    }
    public CategoryDto update ( Integer id, CategoryDto categoryDto ) {

        Category category = categoryRepository
                .findByIdAndUser(id,userDetailsServiceImpl.getUsuarioAutenticado())
                .orElseThrow(() -> new NotFoundException("Not found category ID: : " + id ));

        category.setName(categoryDto.getNombre());
        //category.setIngreso(categoryDto.isIngreso());
        categoryRepository.save(category);

        return new CategoryDto(category);
    }
    public void delete ( int id){
        if ( categoryRepository.findByIdAndUser(id, userDetailsServiceImpl.getUsuarioAutenticado()).isPresent()){
            categoryRepository.deleteById(id);
        }
    }

    //Search Section
    public List<CategoryDto> buscarTodos(){
        List<Category> categories = categoryRepository.findAllByUser(userDetailsServiceImpl.getUsuarioAutenticado());
        List<CategoryDto> categoriesDto = new ArrayList<>();
        categories.forEach(category -> { categoriesDto.add(CategoryDto.builder()
                .id(category.getId())
                .nombre(category.getName())
              //  .ingreso(category.isIngreso())
                .borrado(category.getDeleted()).build());
        });
        return categoriesDto;
    }
    public List<CategoryDto> buscarPorNombre( String nombre){
        List<Category> categories;
        categories = categoryRepository.findByNameContainingAndUser(nombre,userDetailsServiceImpl.getUsuarioAutenticado());
        List<CategoryDto> categoryDtos = new ArrayList<>();

        categories.forEach(concepto -> { categoryDtos.add(CategoryDto.builder()
                .id(concepto.getId())
                .nombre(concepto.getName())
                //.ingreso(concepto.isIngreso())
                .build());
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
        Usuario user = userDetailsServiceImpl.getUsuarioAutenticado();
        Map<String, Category> conceptosEnBDMap =  new HashMap<>();
        categoryRepository
                .findAllByUser(user)
                .forEach( concepto -> conceptosEnBDMap.put(concepto.getName().toUpperCase(),concepto) );

        Category categoryResultado;

        if ( conceptosEnBDMap.containsKey(conceptoDescripcionUpper) ){
            categoryResultado = conceptosEnBDMap.get(conceptoDescripcionUpper);
        }else{
            categoryResultado =  categoryRepository.save(
                    Category.builder()
                            .name(conceptoDescripcion)
                  //          .ingreso(CONCEPTO_TIPO_DEFAULT)
                            .user(user)
                            .build()

            );
        }

        return categoryResultado;
    }
}
