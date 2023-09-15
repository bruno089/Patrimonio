package com.gabru.Patrimonio.utils.validations;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = RangeNullableValidator.class)
public @interface RangeNullable {
    String message () default "Debe ser entre {min} y {max}";
    Class<?>[] groups () default {};
    Class<? extends Payload>[] payload () default {};

    int min () default 0;
    int max () default 100;
    boolean nullable () default false;
}
