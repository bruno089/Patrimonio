package com.gabru.Patrimonio.service;

import com.gabru.Patrimonio.data.entities.Concepto;
import com.gabru.Patrimonio.data.repositories.CategoryRepository;
import com.gabru.Patrimonio.domain.services.CategoryService;
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

    @DisplayName("Get OK concepto ")
    @Test
    void getConcepto () {
        String conceptoEntrante = "Comida";


        List<Concepto> conceptosMock = new ArrayList<>();
        Concepto conceptoMock =  new Concepto();
        conceptoMock.setId(4);
        conceptoMock.setNombre("Comida");
        conceptosMock.add(conceptoMock);

        Mockito.when(categoryRepository.findAll()).thenReturn(conceptosMock);

        Concepto concepto = categoryService.getConcepto(conceptoEntrante);

        assertEquals(4,concepto.getId());

    }

    @DisplayName("Get concepto a traves de concepto entrante con espacios  ")
    @Test
    void getConcepto_test2 () {
        //Entrada
        String conceptoEntranteConEspacios = "   Comida     ";
        //Mocks
        List<Concepto> conceptosMock = new ArrayList<>();
        Concepto conceptoMock =  new Concepto();
        conceptoMock.setId(4);
        conceptoMock.setNombre("Comida");
        conceptosMock.add(conceptoMock);
        Mockito.when(categoryRepository.findAll()).thenReturn(conceptosMock);
        //Proceso
        Concepto concepto = categoryService.getConcepto(conceptoEntranteConEspacios);
        //Aseveracion
        assertEquals(4,concepto.getId());
    }

}
