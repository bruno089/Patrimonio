package com.gabru.Patrimonio.business_controllers;

import com.gabru.Patrimonio.dtos.MovimientoDto;
import com.gabru.Patrimonio.entities.Concepto;
import com.gabru.Patrimonio.entities.Movimiento;
import com.gabru.Patrimonio.repositories.ConceptoRepository;
import com.gabru.Patrimonio.repositories.MovimientoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
@Controller
public class MovimientoController {

    @Autowired    MovimientoRepository movimientoRepository;
    @Autowired    ConceptoRepository conceptoRepository;

    public Movimiento guardar(MovimientoDto movimientoDto) {
        LocalDate fecha = LocalDate.parse(movimientoDto.getFecha(), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        Movimiento movimiento = Movimiento.builder()
                .observacion(movimientoDto.getObservacion())
                .importe(movimientoDto.getImporte())
                .fecha(fecha)
                .alta(LocalDateTime.now())
                .concepto(conceptoRepository.findById(movimientoDto.getConceptoId()).get())
                .build();

        /*Movimiento movimiento = new Movimiento();
        movimiento.setObservacion(movimientoDto.getObservacion());
        movimiento.setImporte(movimientoDto.getImporte());
        LocalDate fecha = LocalDate.parse(movimientoDto.getFecha(), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        movimiento.setFecha(fecha);
        movimiento.setAlta(LocalDateTime.now());
        Concepto concepto = conceptoRepository.findById(movimientoDto.getConceptoId()).get();
        movimiento.setConcepto(concepto);*/

        return movimientoRepository.save(movimiento);
       //Todo Para gabi Agregar un campo, que diga la fecha que se guarda el movimiento, cosa de luego poder hacer un analisis en que momento se cargan los movimientos
    }
}
