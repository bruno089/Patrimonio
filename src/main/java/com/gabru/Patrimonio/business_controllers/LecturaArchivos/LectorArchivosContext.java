package com.gabru.Patrimonio.business_controllers.LecturaArchivos;

import com.gabru.Patrimonio.dtos.MovimientoDto;
import com.gabru.Patrimonio.exceptions.ConflictException;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public class LectorArchivosContext {
    private LectorArchivosStrategy lectorArchivosStrategy;
    public LectorArchivosContext ( String tipoImportacion ) { //aqui factory method?
        if ( tipoImportacion == null ||  tipoImportacion == "" ||  tipoImportacion == " " ){
            throw new ConflictException("Tipo importacion invalido los validos son www.etc.com");
        }
        if ( tipoImportacion == "csv" ) lectorArchivosStrategy = new StrategyCsv();
    }
    public List<MovimientoDto> ejecutar ( MultipartFile archivo){
        return   lectorArchivosStrategy.leerArchivoConvertirMovimientos(archivo);
    }
}
