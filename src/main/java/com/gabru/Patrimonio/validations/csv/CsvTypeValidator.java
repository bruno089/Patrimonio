package com.gabru.Patrimonio.validations.csv;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class CsvTypeValidator  implements ConstraintValidator<CsvType, MultipartFile> {
    @Override
    public void initialize(CsvType constraintAnnotation) {

    }

    @Override
    public boolean isValid(MultipartFile  archivo, ConstraintValidatorContext context) {

        boolean resultado = false;
        String extension = archivo.getContentType();
        //Todo Cambiar el nombre del validador ya q puede ser excel tb
        if (extension.contains("csv") ||extension.contains("application/vnd.ms-excel")){
            resultado = true;
        }
        return resultado;
    }


}
