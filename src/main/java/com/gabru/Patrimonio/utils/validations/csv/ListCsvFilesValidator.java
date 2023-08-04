package com.gabru.Patrimonio.utils.validations.csv;

import org.springframework.web.multipart.MultipartFile;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ListCsvFilesValidator implements ConstraintValidator<ListCsvFiles, MultipartFile[]>{
    @Override
    public void initialize(ListCsvFiles constraintAnnotation) {}

    @Override
    public boolean isValid(MultipartFile[] archivos, ConstraintValidatorContext context) {
        for (MultipartFile archivo: archivos) {
            if (archivo.getContentType().contains("csv") ||
                archivo.getContentType().contains("application/vnd.ms-excel") ){
                return true;
            }
        }
        return false;
    }
}
