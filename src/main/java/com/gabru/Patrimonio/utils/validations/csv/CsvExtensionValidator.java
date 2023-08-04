package com.gabru.Patrimonio.utils.validations.csv;

import org.springframework.web.multipart.MultipartFile;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class CsvExtensionValidator implements ConstraintValidator<CsvExtension, MultipartFile> {
    @Override
    public void initialize(CsvExtension constraintAnnotation) {

    }

    @Override
    public boolean isValid(MultipartFile archivo, ConstraintValidatorContext context) {
        String orgName = archivo.getOriginalFilename();
        String[] parts = orgName.split("\\.");
        String extension = parts[parts.length-1];
        return extension.contains("csv");
    }
}
