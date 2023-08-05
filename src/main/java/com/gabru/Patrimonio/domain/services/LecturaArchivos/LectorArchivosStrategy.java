package com.gabru.Patrimonio.domain.services.LecturaArchivos;

import com.gabru.Patrimonio.api.dtos.TransactionDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
public interface LectorArchivosStrategy {
    List<TransactionDto> leerArchivoConvertirMovimientos ( MultipartFile archivo );


}
