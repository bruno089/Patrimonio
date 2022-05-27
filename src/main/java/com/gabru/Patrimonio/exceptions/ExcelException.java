package com.gabru.Patrimonio.exceptions;


public class ExcelException extends FileException {
    private static final String DESCRIPTION = "File exception";

    public ExcelException(String detail) {
        super(DESCRIPTION + ". " + detail);
    }

}
