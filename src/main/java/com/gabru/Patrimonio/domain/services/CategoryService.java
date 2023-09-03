package com.gabru.Patrimonio.domain.services;

import com.gabru.Patrimonio.api.dtos.CategoryDto;
import com.gabru.Patrimonio.data.entities.Category;
import com.gabru.Patrimonio.data.entities.CategoryGroup;
import com.gabru.Patrimonio.data.entities.Usuario;
import com.gabru.Patrimonio.data.repositories.CategoryGroupRepository;
import com.gabru.Patrimonio.domain.exceptions.ConflictException;
import com.gabru.Patrimonio.domain.exceptions.NotFoundException;
import com.gabru.Patrimonio.data.repositories.CategoryRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@AllArgsConstructor
public class CategoryService {
    CategoryRepository categoryRepository;
    CategoryGroupRepository categoryGroupRepository;
    UserDetailsServiceImpl userDetailsServiceImpl;
    public CategoryDto create ( CategoryDto categoryDto ){

         CategoryGroup categoryGroup = categoryGroupRepository
                .findCategoryGroupByIdAndUser(categoryDto.getCategoryGroupId(),userDetailsServiceImpl.getUserAuth())
                .orElse(null);

        if ( categoryRepository.findByNameAndUser(categoryDto.getName(),userDetailsServiceImpl.getUserAuth()).isPresent()){
            throw new ConflictException("Already exist Category: " + categoryDto.getName());
        }

        Category category =  Category.builder()
                .name(categoryDto.getName())
                .categoryGroup(categoryGroup)
                .user(userDetailsServiceImpl.getUserAuth())
                .build();

        categoryRepository.save(category);

        return new CategoryDto(category);
    }
    public CategoryDto read ( int id){

        Optional<Category> categoryOptional = categoryRepository.findByIdAndUser(id,userDetailsServiceImpl.getUserAuth());

        if (! categoryOptional.isPresent() ){ throw new NotFoundException("Not found category ID: " + id ); }

        return new CategoryDto(categoryOptional.get());
    }
    public CategoryDto update ( Integer id, CategoryDto categoryDto ) {

        Category category = categoryRepository
                .findByIdAndUser(id,userDetailsServiceImpl.getUserAuth())
                .orElseThrow(() -> new NotFoundException("Not found category ID: : " + id ));

        CategoryGroup categoryGroup = categoryGroupRepository
                .findCategoryGroupByIdAndUser(categoryDto.getCategoryGroupId(),userDetailsServiceImpl.getUserAuth())
                .orElse(null);

        category.setCategoryGroup(category.getCategoryGroup() != null ? categoryGroup : null);
        category.setName(categoryDto.getName());

        categoryRepository.save(category);

        return new CategoryDto(category);
    }
    public void delete ( int id ){
        if ( categoryRepository.findByIdAndUser(id, userDetailsServiceImpl.getUserAuth()).isPresent()){
            categoryRepository.deleteById(id);
        }
    }

    //Search Section
    public List<CategoryDto> readAll (){
        List<CategoryDto> categoriesDto = new ArrayList<>();

        List<Category> categories = categoryRepository.findAllByUser(userDetailsServiceImpl.getUserAuth());
        categories.forEach(category -> categoriesDto.add(new CategoryDto(category)));

        return categoriesDto;
    }
    public List<CategoryDto> findByName ( String nombre){

        List<CategoryDto> categoriesDto = new ArrayList<>();

        List<Category> categories = categoryRepository.findByNameContainingAndUser(nombre,userDetailsServiceImpl.getUserAuth());
        categories.forEach(category ->  categoriesDto.add(new CategoryDto(category)) );

        return categoriesDto;
    }
    public Category getCategory ( String categoryName){ //Todo try catch?
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

        if ( categoryName == null || categoryName.isEmpty() ){ return null; }

        //Limpiezas del Concepto, segun se vayan necesitando
        categoryName = categoryName.trim();
        String conceptoDescripcionUpper = categoryName.toUpperCase();
        Usuario user = userDetailsServiceImpl.getUserAuth();
        Map<String, Category> categoryMap =  new HashMap<>();
        categoryRepository
                .findAllByUser(user)
                .forEach( category -> categoryMap.put(category.getName().toUpperCase(),category) );

        Category categoryResult;

        if ( categoryMap.containsKey(conceptoDescripcionUpper) ){
            categoryResult = categoryMap.get(conceptoDescripcionUpper);
        }else{
            categoryResult =  categoryRepository.save(
                    Category.builder()
                            .name(categoryName)
                            .user(user)
                            .build()
            );
        }

        return categoryResult;
    }
}
