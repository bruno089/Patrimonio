package com.gabru.Patrimonio.utils.validations;

public final class Pattern {
    public static final String NINE_DIGITS = "\\d{9}";
    public static final String EMAIL_PATTERN = "\\w+([\\.-]?\\w+)*@\\w+([\\.-]?\\w+)*(\\.\\w{2,3})+$";

    //https://mkyong.com/regular-expressions/how-to-validate-password-with-regular-expression/
    //Debe ser alfanúmerico,longitud entre 5-20 caracteres. Y sin caracteres especiales
    public static final String SEGURIDAD_ALFANUM = "^[a-zA-Z0-9]([._-](?![._-])|[a-zA-Z0-9]){3,18}[a-zA-Z0-9]$";
    //Debe ser alfanumerico, longitud entre 8- 20, contener mayúscula y minúscula. Y un caracter especial
    public static final String SEGURIDAD_ALFANUM_2 = "^[a-zA-Z0-9]([._-](?![._-])|[a-zA-Z0-9]){3,18}[a-zA-Z0-9]$";
    private Pattern() {
        // Nothing to do
    }
}
