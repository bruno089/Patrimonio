package com.gabru.Patrimonio.business_controllers.LecturaArchivos;

import com.gabru.Patrimonio.dtos.MovimientoDto;
import com.gabru.Patrimonio.exceptions.ConflictException;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public class LectorArchivosContext {
    private LectorArchivosStrategy lectorArchivosStrategy;
    private LectorTipo lectorTipo;
    public LectorArchivosContext ( LectorTipo lectorTipo ) {
        this.lectorTipo = lectorTipo;
        lectorArchivosStrategy = this.lectorTipo.createFigure();
    }
    public List<MovimientoDto> ejecutar ( MultipartFile archivo){
        return   lectorArchivosStrategy.leerArchivoConvertirMovimientos(archivo);
    }




}
