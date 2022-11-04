package com.gabru.Patrimonio.validations.csv;

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
    //Todo si agrego que sea de tipo txt puedo meter las presentacinoes asi como se bajan de la app de Farmacia
    String message () default "The file has no CSV extension.";

    Class<?>[] groups () default {};

    Class<? extends Payload>[] payload () default {};
}
