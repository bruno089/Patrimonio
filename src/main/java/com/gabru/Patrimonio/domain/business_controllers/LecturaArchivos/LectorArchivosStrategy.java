package com.gabru.Patrimonio.business_controllers.LecturaArchivos;

import com.gabru.Patrimonio.api.dtos.TransactionDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
public interface LectorArchivosStrategy {
    List<TransactionDto> leerArchivoConvertirMovimientos ( MultipartFile archivo );


}
