package com.gabru.Patrimonio.business_controllers.LecturaArchivos;

import com.gabru.Patrimonio.business_controllers.LecturaArchivos.LectorArchivosStrategy;
import com.gabru.Patrimonio.dtos.MovimientoDto;
import com.gabru.Patrimonio.exceptions.ConflictException;
import com.gabru.Patrimonio.utils.GestorCSV;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
public class StrategyCsv implements LectorArchivosStrategy {
    GestorCSV gestorCSV = new GestorCSV();
    List<MovimientoDto> movimientoDtos = new ArrayList<>();
    @Override
    public List<MovimientoDto> leerArchivoConvertirMovimientos ( MultipartFile archivo ) {
        try {
            List<CSVRecord> registros = gestorCSV.getRegistrosCsv(archivo, Charset.forName("Cp1252"), CSVFormat.newFormat(';').withFirstRecordAsHeader() );

            registros.forEach( registro -> { movimientoDtos.add(this.nuevoMovimientoDto(registro)); } );
        }catch (IOException e) { throw new RuntimeException(e); }
        return movimientoDtos;
    }
    private MovimientoDto nuevoMovimientoDto ( CSVRecord registro ){

        String fecha;
        Double importe;
        String observacion;
        String concepto;

        try {
            fecha = registro.get(0);
            importe = Double.parseDouble(registro.get(1));
            observacion = registro.get(2);
            concepto  = registro.get(3);

        }catch ( ConflictException e ){ throw new ConflictException("Error en conversion del Movimiento"); }

        return MovimientoDto.builder()
                .fecha(fecha)
                .importe(importe)
                .observacion(observacion)
                .conceptoDescripcion(concepto)
                .build();
    }

}