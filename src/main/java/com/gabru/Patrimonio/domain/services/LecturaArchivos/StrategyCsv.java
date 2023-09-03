package com.gabru.Patrimonio.domain.services.LecturaArchivos;

import com.gabru.Patrimonio.api.dtos.TransactionDto;
import com.gabru.Patrimonio.domain.exceptions.ConflictException;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
public class StrategyCsv implements LectorArchivosStrategy {
    List<TransactionDto> transactionDtos = new ArrayList<>();
    @Override
    public List<TransactionDto> leerArchivoConvertirMovimientos ( MultipartFile archivo ) {
        try {
            List<CSVRecord> registros = this.getRegistrosCsv(archivo, Charset.forName("Cp1252"), CSVFormat.newFormat(';').withFirstRecordAsHeader() );

            registros.forEach( registro -> { transactionDtos.add(this.nuevoMovimientoDto(registro)); } );
        }catch (IOException e) { throw new RuntimeException(e); }
        return transactionDtos;
    }
    private TransactionDto nuevoMovimientoDto ( CSVRecord registro ){

        String fecha;
        Double importe;
        String observacion;
        String concepto;

        try {
            fecha = registro.get(0);
            importe = Double.parseDouble(registro.get(1));
            observacion = registro.get(2);
            concepto  = registro.get(3);

        }catch ( ConflictException e ){
            throw new ConflictException("Error en conversion del Movimiento");
        }
        catch ( Exception e ){
            throw new RuntimeException("StrategyCsv.leerArchivoConvertirMovimientos: Error en conversion " + registro.toString() + e.getMessage());
        }

        return TransactionDto.builder()
                .date(fecha)
                .amount(importe)
                .detail(observacion)
                .categoryName(concepto)
                .build();
    }

    // GestorCSV Class
    public List<CSVRecord> getRegistrosCsv (MultipartFile file, Charset charset, CSVFormat format) throws IOException {
        List<CSVRecord> registros = new ArrayList<>();
        try {
            CSVParser parser = CSVParser.parse(file.getInputStream(), charset,format);
            registros = parser.getRecords();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return registros;
    }

}
