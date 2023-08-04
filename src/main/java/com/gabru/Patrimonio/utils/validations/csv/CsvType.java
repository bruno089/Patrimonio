package com.gabru.Patrimonio.utils.validations.csv;
import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target ({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = CsvTypeValidator.class)
    public @interface CsvType {

    String message () default "Esperado un archivo un archivo tipo csv";

    Class<?>[] groups () default {};

    Class<? extends Payload>[] payload () default {};

}
