package com.gabru.Patrimonio.domain.exceptions;


public class ExcelException extends FileException {
    private static final String DESCRIPTION = "File exception";

    public ExcelException(String detail) {
        super(DESCRIPTION + ". " + detail);
    }

}
