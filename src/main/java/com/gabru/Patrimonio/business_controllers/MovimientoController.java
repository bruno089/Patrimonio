package com.gabru.Patrimonio.business_controllers;

import com.gabru.Patrimonio.dtos.MovimientoDto;
import com.gabru.Patrimonio.entities.Concepto;
import com.gabru.Patrimonio.entities.Movimiento;
import com.gabru.Patrimonio.repositories.ConceptoRepository;
import com.gabru.Patrimonio.repositories.MovimientoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
@Controller
public class MovimientoController {

    @Autowired    MovimientoRepository movimientoRepository;
    @Autowired    ConceptoRepository conceptoRepository;

    public Movimiento guardar(MovimientoDto movimientoDto) {
        Movimiento movimiento = new Movimiento();
        movimiento.setObservacion(movimientoDto.getObservacion());
        movimiento.setImporte(movimientoDto.getImporte());
        movimiento.setFecha(LocalDate.now());
        Concepto concepto = conceptoRepository.findById(movimientoDto.getConceptoId()).get();
        movimiento.setConcepto(concepto);
        return movimientoRepository.save(movimiento);
       // LocalDate localDate = LocalDate.parse(fecha, DateTimeFormatter.ofPattern(patronDeEntrada));
       //Todo Para gabi Agregar un campo, que diga la fecha que se guarda el movimiento, cosa de luego poder hacer un analisis en que momento se cargan los movimientos
    }
}
