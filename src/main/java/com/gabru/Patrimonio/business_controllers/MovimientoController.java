package com.gabru.Patrimonio.business_controllers;

import com.gabru.Patrimonio.business_controllers.LecturaArchivos.LectorArchivosContext;
import com.gabru.Patrimonio.business_controllers.LecturaArchivos.LectorTipo;
import com.gabru.Patrimonio.business_controllers.LecturaArchivos.StrategyCsv;
import com.gabru.Patrimonio.dtos.MovimientoDto;
import com.gabru.Patrimonio.dtos.MovimientosTotalesPorConceptoDto;
import com.gabru.Patrimonio.entities.Concepto;
import com.gabru.Patrimonio.entities.Movimiento;


import com.gabru.Patrimonio.exceptions.NotFoundException;

import com.gabru.Patrimonio.repositories.ConceptoRepository;
import com.gabru.Patrimonio.repositories.MovimientoRepository;
import com.gabru.Patrimonio.repositories.MovimientoRepositoryCustom;
import com.gabru.Patrimonio.utils.FechaConverter;
import com.gabru.Patrimonio.utils.GestorCSV;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static com.gabru.Patrimonio.utils.FechaConverter.stringtoLocalDate;

@Controller
@AllArgsConstructor
public class MovimientoController {
    MovimientoRepository movimientoRepository;
    ConceptoRepository conceptoRepository;
    MovimientoRepositoryCustom movimientoRepositoryCustom;
    public static final boolean CONCEPTO_TIPO_DEFAULT = false;

    public void CsvAMovimientoDtoList ( MultipartFile archivo, String tipoImportacion ){

        LectorArchivosContext lectorArchivosContext = new LectorArchivosContext(LectorTipo.CSV );

        List<MovimientoDto> movimientoDtos =  lectorArchivosContext.ejecutar(archivo);

        movimientoDtos.forEach( movimientoDto -> this.agregar( movimientoDto));
    }
    public MovimientoDto agregar( MovimientoDto movimientoDto) { //Todo Try para manejar excepciones
        // Todo Permitir que guarde una lista utilizar saveall()?
        LocalDate fecha = LocalDate.parse(movimientoDto.getFecha(), DateTimeFormatter.ofPattern("dd/MM/yyyy"));

        Concepto newConcepto =  this.getConcepto(movimientoDto.getConceptoDescripcion());

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

        Concepto elConcepto =  this.getConcepto(movimientoDto.getConceptoDescripcion());

        movimiento.setConcepto(elConcepto);
        movimiento.setFecha(FechaConverter.stringtoLocalDate(movimientoDto.getFecha(), "dd/MM/yyyy"));
        movimiento.setImporte(movimientoDto.getImporte());
        movimiento.setObservacion(movimientoDto.getObservacion());
        movimientoRepository.save(movimiento);

        return new MovimientoDto(movimiento);
    }

    public List<MovimientoDto> buscarMovimientosPorFecha(String fechaInicial, String fechaFinal) {
        LocalDate fechaIni = stringtoLocalDate(fechaInicial,"yyyy-MM-dd");
        LocalDate fechaFin = stringtoLocalDate(fechaFinal,"yyyy-MM-dd");

        List<MovimientoDto> movimientos = movimientoRepository.findAllByFechaBetweenOrderByFecha(fechaIni,fechaFin);

        return movimientos;
    }

    public Concepto getConcepto(String conceptoDescripcion){
        /** Concepto  - Servicio
         *
         * El manejo de concepto tiene q estar nucleado en un solo lugar (Principio de Unica Responsabilidad)
         * El servicio se debe de encargar de devolver el concepto en base a su descripcion. Debe poder distinguir entre minusculas y mayusculas
         *          *  Ademas si el concepto no existe en BD se debe guardar este concepto
         * Cuidado:
         * - Con los conceptos con espacios --
         * - Case sensitive                 --
         * - En plural y/o en singular      xx
         * - Con muchas llamadas a BD       --
         * *
         * */
        //Es caseSensitive?
        Concepto conceptoResultado;

        Map<String,Concepto> conceptosMap =  new HashMap<>(); //Tengo los conceptos existentes en BD
        conceptoRepository.findAll().forEach( concepto -> conceptosMap.put(concepto.getNombre(),concepto) );

        if ( conceptosMap.containsKey(conceptoDescripcion) ){
            conceptoResultado = conceptosMap.get(conceptoDescripcion);
        }else{ //guardo por que no existe
            conceptoResultado =  conceptoRepository.save(Concepto.builder().nombre(conceptoDescripcion).ingreso(CONCEPTO_TIPO_DEFAULT).build());
        }
        /*
        Optional<Concepto> concepto = conceptoRepository.findById(movimientoDto.getIdConcepto()); //  Todo Arreglar aqui
        if (!concepto.isPresent()){
            throw new ConflictException("idConcepto inexistente: "+  movimientoDto.getIdConcepto() );  //  Todo Arreglar aqui
        }*/
        return  conceptoResultado;
    }

    public List<MovimientosTotalesPorConceptoDto>  totalizador( String fechaInicial, String fechaFinal){

        LocalDate fechaIni = stringtoLocalDate(fechaInicial,"d/M/yyyy");
        LocalDate fechaFin = stringtoLocalDate(fechaFinal,"d/M/yyyy");

        List<MovimientosTotalesPorConceptoDto>  resultado = movimientoRepositoryCustom.findByFechasBetweenGroupByMonth(fechaIni,fechaFin);

        return resultado;
    }

}
