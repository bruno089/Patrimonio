package com.gabru.Patrimonio.business_controllers;

import com.gabru.Patrimonio.dtos.MovimientoDto;
import com.gabru.Patrimonio.entities.Movimiento;
import com.gabru.Patrimonio.repositories.ConceptoRepository;
import com.gabru.Patrimonio.repositories.MovimientoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Controller
public class MovimientoController {
    @Autowired    MovimientoRepository movimientoRepository;
    @Autowired    ConceptoRepository conceptoRepository;
    public MovimientoDto guardar(MovimientoDto movimientoDto) {
        LocalDate fecha = LocalDate.parse(movimientoDto.getFecha(), DateTimeFormatter.ofPattern("yyyy-MM-dd"));

        Movimiento movimiento = Movimiento.builder()
                .observacion(movimientoDto.getObservacion())
                .importe(movimientoDto.getImporte())
                .fecha(fecha)
                .alta(LocalDateTime.now())
                .concepto(conceptoRepository.findById(movimientoDto.getIdConcepto()).get())
                .build();

        movimientoRepository.save(movimiento);


        return new MovimientoDto(movimiento);
       //Todo Para gabi Agregar un campo, que diga la fecha que se guarda el movimiento, cosa de luego poder hacer un analisis en que momento se cargan los movimientos
    }

    public void borrar(int movimientoId) {
        movimientoRepository.delete(movimientoRepository.getOne(movimientoId));
    }
}
