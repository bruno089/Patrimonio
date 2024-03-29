package com.gabru.Patrimonio.domain.services.LecturaArchivos;

import com.gabru.Patrimonio.api.dtos.TransactionDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public class LectorArchivosContext {
    private LectorArchivosStrategy lectorArchivosStrategy;
    private LectorTipo lectorTipo;
    public LectorArchivosContext ( LectorTipo lectorTipo ) {
        this.lectorTipo = lectorTipo;
        lectorArchivosStrategy = this.lectorTipo.create();
    }
    public List<TransactionDto> read ( MultipartFile archivo){
        return   lectorArchivosStrategy.leerArchivoConvertirMovimientos(archivo);
    }




}
