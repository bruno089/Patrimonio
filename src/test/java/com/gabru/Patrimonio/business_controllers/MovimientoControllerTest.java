package com.gabru.Patrimonio.business_controllers;

import com.gabru.Patrimonio.entities.Movimiento;
import com.gabru.Patrimonio.repositories.MovimientoRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
class MovimientoControllerTest {
    @Mock           MovimientoRepository movimientoRepository;
    @InjectMocks    MovimientoController movimientoController ;

    @Test
    void buscarMovimientosPorFecha_OK_Test() {

        String fechaInicial = "2022-01-01";
        String fechaFinal = "2022-06-01";

        List<Movimiento> movimientos = new ArrayList<>();
        movimientos.add(Movimiento.builder().id(6).importe(100.00) .build());
        Mockito.when(movimientoRepository.findAllByFechaBetween(Mockito.any(),Mockito.any())).thenReturn(movimientos);

        List<Movimiento> resultado = movimientoController.buscarMovimientosPorFecha(fechaInicial,fechaFinal);

        Assertions.assertNotNull(resultado);
    }
    @Test
    void buscarMovimientosPorFecha_fechaInvalida_Test() {
        String fechaInicial = "SinFormato";
        String fechaFinal = "2022-06-01";

        assertThrows( DateTimeParseException.class, () -> { movimientoController.buscarMovimientosPorFecha(fechaInicial,fechaFinal); });
    }



}