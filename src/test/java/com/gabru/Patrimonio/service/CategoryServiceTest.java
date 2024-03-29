package com.gabru.Patrimonio.service;

import com.gabru.Patrimonio.api.dtos.CategoryDto;
import com.gabru.Patrimonio.data.entities.Category;
import com.gabru.Patrimonio.data.entities.Usuario;
import com.gabru.Patrimonio.data.repositories.CategoryRepository;
import com.gabru.Patrimonio.domain.services.CategoryService;
import com.gabru.Patrimonio.domain.services.UserDetailsServiceImpl;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(MockitoExtension.class)
class CategoryServiceTest {
    @Mock
    CategoryRepository categoryRepository;
    @InjectMocks
    CategoryService categoryService;

    @Mock    UserDetailsServiceImpl userDetailsService;

    @DisplayName("Get OK concepto ") @Disabled
    @Test
    void getConcepto () {
        CategoryDto categoryDto = new CategoryDto();
        categoryDto.setName("Comida");
        categoryDto.setCategoryGroupId(1);

        List<Category> conceptosMock = new ArrayList<>();
        Category categoryMock =  new Category();
        categoryMock.setId(4);
        categoryMock.setName("Comida");
        conceptosMock.add(categoryMock);

        Mockito.when(categoryRepository.findAllByUser(Mockito.any())).thenReturn(conceptosMock);

        Category category = categoryService.findByNameOrSaveCategory(categoryDto);

        assertEquals(4, category.getId());

    }

    @DisplayName("Get concepto a traves de concepto entrante con espacios  ") @Disabled
    @Test
    void getConcepto_test2 () {

        //Entrada
        CategoryDto categoryDto = new CategoryDto();
        categoryDto.setName("   Comida   ");
        categoryDto.setCategoryGroupId(1);
        //Mocks
        Usuario user = new Usuario();
        user.setNombre("Jhon");
        List<Category> conceptosMock = new ArrayList<>();
        Category categoryMock =  new Category();
        categoryMock.setId(4);
        categoryMock.setName("Comida");
        categoryMock.setUser(user);
        conceptosMock.add(categoryMock);
        Mockito.when(categoryRepository.findAllByUser(Mockito.any())).thenReturn(conceptosMock);
        //Proceso
        Category category = categoryService.findByNameOrSaveCategory(categoryDto);
        //Aseveracion
        assertEquals(4, category.getId());
    }

}
