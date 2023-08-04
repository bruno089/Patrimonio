package com.gabru.Patrimonio.api.dtos;

import com.gabru.Patrimonio.utils.validations.NotEmptyFile;
import com.gabru.Patrimonio.utils.validations.csv.CsvExtension;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;

@Data @AllArgsConstructor @NoArgsConstructor @Builder
public class ArchivoDto {
    @NotNull(message = "archivo requerido")
    @NotEmptyFile()
    @CsvExtension
    MultipartFile archivo;
}
