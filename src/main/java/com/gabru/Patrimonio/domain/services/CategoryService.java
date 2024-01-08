package com.gabru.Patrimonio.domain.services;

import com.gabru.Patrimonio.api.dtos.CategoryDto;
import com.gabru.Patrimonio.api.dtos.CategoryGroupDto;
import com.gabru.Patrimonio.data.entities.Category;
import com.gabru.Patrimonio.data.entities.CategoryGroup;
import com.gabru.Patrimonio.data.entities.Usuario;
import com.gabru.Patrimonio.data.repositories.CategoryGroupRepository;
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


    public CategoryDto createCategory ( CategoryDto categoryDto ){
        return  new CategoryDto(this.create(categoryDto) );
    }
    private Category create(CategoryDto categoryDto) {
        //Todo al crear si ya tiene asingado null una groupCategory, no se le puede asignar otra desde CSV import
        Usuario user = userDetailsServiceImpl.getUserAuth();

        CategoryGroup categoryGroup = null;

        if (categoryDto.getCategoryGroupId() != null || categoryDto.getCategoryGroupName() != null) {

            Optional<CategoryGroup> optionalCategoryGroup = categoryGroupRepository
                    .findByIdAndUserOrNameAndUser(categoryDto.getCategoryGroupId(),categoryDto.getCategoryGroupName(), user);

            categoryGroup = optionalCategoryGroup.orElseGet(() -> categoryGroupRepository.save(CategoryGroup.builder()
                    .name(categoryDto.getCategoryGroupName())
                    .user(user)
                    .build()));
        }

        Optional<Category> optionalCategory = categoryRepository.findByNameAndUserCleanedName(categoryDto.getName(), user);

        Category category;
        if (optionalCategory.isPresent()) {

            category = optionalCategory.get();

            if ( category.getCategoryGroup() == null && categoryGroup != null ) {
                category.setCategoryGroup(categoryGroup);
            }
        } else {   //Central Category creation
             category = Category.builder()
                    .name(categoryDto.getName())
                    .categoryGroup(categoryGroup)
                    .user(user)
                    .build();

        }
        categoryRepository.save(category);
        return category;
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
                .findByIdAndUserOrNameAndUser(categoryDto.getCategoryGroupId(),categoryDto.getCategoryGroupName(), userDetailsServiceImpl.getUserAuth())
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
    public Category findByNameOrSaveCategory ( CategoryDto categoryDto) {
        if (categoryDto == null || categoryDto.getName() == null || categoryDto.getName().isEmpty()) {
            return null;
        }

        Usuario user = userDetailsServiceImpl.getUserAuth();
        Optional<Category> optionalCategory = categoryRepository.findByNameAndUserCleanedName(categoryDto.getName(), user);

        if (optionalCategory.isPresent()) {
            return optionalCategory.get();
        } else {
            return create(categoryDto);
        }
    }
    //Search Section
    public List<CategoryDto> readAll (){
        List<CategoryDto> categoriesDto = new ArrayList<>();

        List<Category> categories = categoryRepository.findAllByUser(userDetailsServiceImpl.getUserAuth());
        categories.forEach(category -> categoriesDto.add(new CategoryDto(category)));

        return categoriesDto;
    }
    public List<CategoryDto> readAllByName ( String nombre){

        List<CategoryDto> categoriesDto = new ArrayList<>();

        List<Category> categories = categoryRepository.findByNameContainingAndUser(nombre,userDetailsServiceImpl.getUserAuth());
        categories.forEach(category ->  categoriesDto.add(new CategoryDto(category)) );

        return categoriesDto;
    }



}
