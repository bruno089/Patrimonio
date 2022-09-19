package com.gabru.Patrimonio.business_controllers;

import com.gabru.Patrimonio.dtos.MovimientoDto;
import com.gabru.Patrimonio.entities.Concepto;
import com.gabru.Patrimonio.entities.Movimiento;
import com.gabru.Patrimonio.repositories.ConceptoRepository;
import com.gabru.Patrimonio.repositories.MovimientoRepository;
import com.gabru.Patrimonio.utils.GestorCSV;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.charset.Charset;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static com.gabru.Patrimonio.utils.FechaConverter.stringtoLocalDate;

@Controller
public class MovimientoController {
    @Autowired    MovimientoRepository movimientoRepository;
    @Autowired    ConceptoRepository conceptoRepository;
    @Autowired    GestorCSV gestorCSV;

    public static final boolean CONCEPTO_TIPO_DEFAULT = false;

    //Todo Permitir que guarde una lista utilizar saveall()?
    public MovimientoDto agregar( MovimientoDto movimientoDto) { //Todo Try para manejar excepciones

        //LocalDate fecha = LocalDate.parse(movimientoDto.getFecha(), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        LocalDate fecha = LocalDate.parse(movimientoDto.getFecha(), DateTimeFormatter.ofPattern("dd/MM/yy"));

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
    public List<Movimiento> buscarMovimientosPorFecha(String fechaInicial, String fechaFinal) {

        LocalDate fechaIni = stringtoLocalDate(fechaInicial,"yyyy-MM-dd");
        LocalDate fechaFin = stringtoLocalDate(fechaFinal,"yyyy-MM-dd");

        List<Movimiento> movimientos = movimientoRepository.findAllByFechaBetween(fechaIni,fechaFin);

        return movimientos;
    }
    public void CsvAMovimientoDtoList ( MultipartFile archivo )  {

        List<MovimientoDto> movimientoDtos = new ArrayList<>();

        try {

            List<CSVRecord> registros = gestorCSV.getRegistrosCsv(archivo, Charset.forName("Cp1252"), CSVFormat.newFormat(';').withFirstRecordAsHeader() );

            registros.forEach( registro -> { movimientoDtos.add(this.nuevoMovimientoDto(registro)); } );

            movimientoDtos.forEach( movimientoDto -> this.agregar( movimientoDto ));

        }catch (IOException e) { throw new RuntimeException(e); }
    }
    private MovimientoDto nuevoMovimientoDto ( CSVRecord registro){ //Todo Faltan excepciones convirtiendo ?

        String fecha = registro.get(0);
        Double importe = Double.parseDouble(registro.get(1));
        String observacion = registro.get(2);
        String concepto  = registro.get(3);

        return MovimientoDto.builder().fecha(fecha).importe(importe).observacion(observacion).conceptoDescripcion(concepto).build();
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

    //Todo Rehacer con softdelete
    public void borrar(int movimientoId) {movimientoRepository.delete(movimientoRepository.getOne(movimientoId));
    }

}
