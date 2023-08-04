package com.gabru.Patrimonio.utils.validations;

import org.springframework.web.multipart.MultipartFile;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class FilesListTxtTypeValidator implements ConstraintValidator<FilesListTxtType, MultipartFile[]> {
    @Override
    public void initialize(FilesListTxtType constraintAnnotation) {

    }

    @Override
    public boolean isValid(MultipartFile[] archivos, ConstraintValidatorContext context) {

        for (MultipartFile archivo: archivos) {
            if (archivo.getContentType().contains("text/plain")){
                return true;
            }
        }
        return false;
    }
}
