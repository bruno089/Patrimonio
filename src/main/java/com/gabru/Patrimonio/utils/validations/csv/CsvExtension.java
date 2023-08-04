package com.gabru.Patrimonio.utils.validations.csv;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = CsvExtensionValidator.class)
public @interface CsvExtension {
    String message () default "The file has no CSV extension.";

    Class<?>[] groups () default {};

    Class<? extends Payload>[] payload () default {};
}
