package com.gabru.Patrimonio.validations;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = FilesListTxtTypeValidator.class)
public @interface FilesListTxtType {

    String message () default "No todos los archivos son del tipo txt.";

    Class<?>[] groups () default {};

    Class<? extends Payload>[] payload () default {};

}
