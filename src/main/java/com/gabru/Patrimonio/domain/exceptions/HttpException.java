package com.gabru.Patrimonio.exceptions;

public class HttpException extends RuntimeException{

    private static final String DESCRIPTION = "HttpException";

    public HttpException(String detail) {
        super(DESCRIPTION + ". " + detail);
    }
}
