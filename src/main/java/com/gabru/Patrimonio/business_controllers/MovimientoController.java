package com.gabru.Patrimonio.business_controllers;

import com.gabru.Patrimonio.business_controllers.LecturaArchivos.LectorArchivosContext;
import com.gabru.Patrimonio.business_controllers.LecturaArchivos.LectorTipo;
import com.gabru.Patrimonio.dtos.MovimientoDto;
import com.gabru.Patrimonio.dtos.MovimientosTotalesPorConceptoDto;
import com.gabru.Patrimonio.entities.Concepto;
import com.gabru.Patrimonio.entities.Movimiento;


import com.gabru.Patrimonio.exceptions.NotFoundException;

import com.gabru.Patrimonio.repositories.MovimientoRepository;
import com.gabru.Patrimonio.repositories.MovimientoRepositoryCustom;
import com.gabru.Patrimonio.service.ConceptoService;
import com.gabru.Patrimonio.business_services.FechaConverterService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

import static com.gabru.Patrimonio.business_services.FechaConverterService.stringtoLocalDate;

@Controller
@AllArgsConstructor
public class MovimientoController {
    MovimientoRepository movimientoRepository;
    MovimientoRepositoryCustom movimientoRepositoryCustom;
    ConceptoService conceptoService;
    public void CsvAMovimientoDtoList ( MultipartFile archivo, String tipoImportacion ){

        LectorArchivosContext lectorArchivosContext = new LectorArchivosContext(LectorTipo.CSV );

        List<MovimientoDto> movimientoDtos =  lectorArchivosContext.ejecutar(archivo);

        movimientoDtos.forEach( movimientoDto -> this.agregar( movimientoDto));
    }

    public MovimientoDto agregar( MovimientoDto movimientoDto) { //Todo Try para manejar excepciones
        // Todo Permitir que guarde una lista utilizar saveall()?
        LocalDate fecha = LocalDate.parse(movimientoDto.getFecha(), DateTimeFormatter.ofPattern("dd/MM/yyyy"));

        Concepto newConcepto =  conceptoService.getConcepto(movimientoDto.getConceptoDescripcion());

        Movimiento movimiento = Movimiento.builder()
                .observacion(movimientoDto.getObservacion())
                .importe(movimientoDto.getImporte())
                .fecha(fecha)
                .alta(LocalDateTime.now())
                .concepto(newConcepto)
                .build();

        movimientoRepository.save(movimiento);

        return new MovimientoDto(movimiento);
    }

    public void borrar(int movimientoId) {
        //Todo validaciones y manejo de excepeciones de borrado
        movimientoRepository.delete(movimientoRepository.getOne(movimientoId));
    }

    public MovimientoDto actualizar(int id, MovimientoDto movimientoDto) {
        Movimiento movimiento = movimientoRepository.findById(id).orElseThrow(()-> new NotFoundException("No se encuentra el movimiento con ID: " + id));

        Concepto elConcepto =  conceptoService.getConcepto(movimientoDto.getConceptoDescripcion());

        movimiento.setConcepto(elConcepto);
        movimiento.setFecha(FechaConverterService.stringtoLocalDate(movimientoDto.getFecha(), "dd/MM/yyyy"));
        movimiento.setImporte(movimientoDto.getImporte());
        movimiento.setObservacion(movimientoDto.getObservacion());
        movimientoRepository.save(movimiento);

        return new MovimientoDto(movimiento);
    }

    public List<MovimientoDto> buscarMovimientosPorFecha(String fechaInicial, String fechaFinal) {
        LocalDate fechaIni = stringtoLocalDate(fechaInicial,"d/M/yyyy");
        LocalDate fechaFin = stringtoLocalDate(fechaFinal,"d/M/yyyy");

        List<MovimientoDto> movimientos = movimientoRepository.findAllByFechaBetweenOrderByFecha(fechaIni,fechaFin);

        return movimientos;
    }

    public List<MovimientosTotalesPorConceptoDto> totalizador( String fechaInicial, String fechaFinal){
        LocalDate fechaIni = stringtoLocalDate(fechaInicial,"d/M/yyyy");
        LocalDate fechaFin = stringtoLocalDate(fechaFinal,"d/M/yyyy");

        List<MovimientosTotalesPorConceptoDto>  resultado = movimientoRepositoryCustom.findByFechasBetweenGroupByMonth(fechaIni,fechaFin);

        return resultado;
    }

    public List<MovimientoDto> buscarTodos(){ //Todo hacer esto como en PERSONAS API, con paginado y sorting
        List<Movimiento> movimientos = movimientoRepository.findTop10ByOrderByIdDesc();
        return movimientos.stream()
                .map(MovimientoDto::new)
                .collect(Collectors.toList());
    }

    public MovimientoDto buscar ( Integer movimientoId ) {

        Movimiento movimiento = movimientoRepository
                .findById(movimientoId)
                .orElseThrow(() -> new NotFoundException("No encontrado id:"+ movimientoId));

        return  new MovimientoDto(movimiento);
    }
}
