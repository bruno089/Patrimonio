package com.gabru.Patrimonio.utils.validations;

import org.springframework.web.multipart.MultipartFile;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class FilesListNotEmptyValidator implements ConstraintValidator<FilesListNotEmpty, MultipartFile[]> {

    @Override
    public void initialize(FilesListNotEmpty constraintAnnotation) {

    }

    @Override
    public boolean isValid(MultipartFile[] files, ConstraintValidatorContext context) {

        for (MultipartFile file:files) {
            if (file.isEmpty()){
                return false;
            }
        }
        return true;
    }
}
