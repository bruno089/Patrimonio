package com.gabru.Patrimonio.validations;

import org.springframework.web.multipart.MultipartFile;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class NotEmptyFileValidator  implements ConstraintValidator<NotEmptyFile, MultipartFile> {
    @Override
    public void initialize(NotEmptyFile constraintAnnotation) {

    }

    @Override
    public boolean isValid(MultipartFile value, ConstraintValidatorContext context) {
        return ! value.isEmpty();
    }
}
