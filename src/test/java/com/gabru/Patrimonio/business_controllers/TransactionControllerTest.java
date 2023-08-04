package com.gabru.Patrimonio.business_controllers;

import com.gabru.Patrimonio.api.dtos.TransactionDto;
import com.gabru.Patrimonio.data.repositories.TransactionRepository;
import com.gabru.Patrimonio.domain.business_controllers.TransactionService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class TransactionControllerTest {
    @Mock
    TransactionRepository transactionRepository;

    @InjectMocks
    TransactionService transactionService;
    @Test
    void buscarMovimientosPorFecha_OK_Test() {
        String fechaInicial = "01/01/2022";
        String fechaFinal = "06/01/2022";

        List<TransactionDto> movimientos = new ArrayList<>();
        movimientos.add(TransactionDto.builder().id(6).importe(100.00).observacion("pago de helado").build());
        Mockito.when(transactionRepository.findAllByFechaBetweenOrderByFecha(Mockito.any(),Mockito.any())).thenReturn(movimientos);

        List<TransactionDto> resultado = transactionService.buscarMovimientosPorFecha(fechaInicial,fechaFinal);

        Assertions.assertNotNull(resultado);
        assertEquals(100.00, resultado.get(0).getImporte());
    }

    @Test
    void buscarMovimientosPorFecha_fechaInvalida_Test() {
        String fechaInicial = "SinFormato";
        String fechaFinal = "06/01/2022";

        assertThrows( DateTimeParseException.class, () -> { transactionService.buscarMovimientosPorFecha(fechaInicial,fechaFinal); });
    }

}
