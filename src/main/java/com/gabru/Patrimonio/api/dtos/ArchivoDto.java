package com.gabru.Patrimonio.api.file.dtos;

import com.gabru.Patrimonio.validations.NotEmptyFile;
import com.gabru.Patrimonio.validations.csv.CsvExtension;
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
