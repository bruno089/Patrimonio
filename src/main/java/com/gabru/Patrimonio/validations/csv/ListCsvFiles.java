package com.gabru.Patrimonio.validations.csv;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ListCsvFilesValidator.class)
public @interface ListCsvFiles {

    String message () default "No todos los archivos son de tipo CSV.";

    Class<?>[] groups () default {};

    Class<? extends Payload>[] payload () default {};
}
