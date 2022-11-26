package com.gabru.Patrimonio.business_controllers.LecturaArchivos;

import com.gabru.Patrimonio.dtos.MovimientoDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
public interface LectorArchivosStrategy {
    List<MovimientoDto> leerArchivoConvertirMovimientos ( MultipartFile archivo );


}
