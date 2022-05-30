package com.gabru.Patrimonio.business_controllers;

import com.gabru.Patrimonio.dtos.MovimientoDto;
import com.gabru.Patrimonio.entities.Concepto;
import com.gabru.Patrimonio.entities.Movimiento;
import com.gabru.Patrimonio.exceptions.ConflictException;
import com.gabru.Patrimonio.repositories.ConceptoRepository;
import com.gabru.Patrimonio.repositories.MovimientoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

@Controller
public class MovimientoController {
    @Autowired    MovimientoRepository movimientoRepository;
    @Autowired    ConceptoRepository conceptoRepository;
    public MovimientoDto guardar(MovimientoDto movimientoDto) {

        LocalDate fecha = LocalDate.parse(movimientoDto.getFecha(), DateTimeFormatter.ofPattern("yyyy-MM-dd"));

        Optional<Concepto> concepto = conceptoRepository.findById(movimientoDto.getIdConcepto());

        if (!concepto.isPresent()){
            throw new ConflictException("idConcepto inexistente: "+  movimientoDto.getIdConcepto() );
        }

        Movimiento movimiento = Movimiento.builder()
                .observacion(movimientoDto.getObservacion())
                .importe(movimientoDto.getImporte())
                .fecha(fecha)
                .alta(LocalDateTime.now())
                .concepto(concepto.get())
                .build();

        movimientoRepository.save(movimiento);

        return new MovimientoDto(movimiento);
    }

    public void borrar(int movimientoId) {
        movimientoRepository.delete(movimientoRepository.getOne(movimientoId));
    }
}
