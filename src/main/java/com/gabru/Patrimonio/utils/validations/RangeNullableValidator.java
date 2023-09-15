package com.gabru.Patrimonio.utils.validations;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class RangeNullableValidator implements ConstraintValidator<RangeNullable, Integer> {
    private int min;
    private int max;
    private boolean nullable;
    @Override
    public void initialize ( RangeNullable rangeNullable ) {
        this.min = rangeNullable.min();
        this.max = rangeNullable.max();
        this.nullable = rangeNullable.nullable();
    }
    @Override
    public boolean isValid(Integer valor, ConstraintValidatorContext context) {
        if (valor == null) {  // Si es nulo, la validez depende de la configuración de "nullable"
            return nullable;
        }

        return valor >= min && valor <= max;
    }
}
