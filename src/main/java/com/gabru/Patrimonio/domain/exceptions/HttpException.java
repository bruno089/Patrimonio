package com.gabru.Patrimonio.domain.exceptions;

public class HttpException extends RuntimeException{

    private static final String DESCRIPTION = "HttpException";

    public HttpException(String detail) {
        super(DESCRIPTION + ". " + detail);
    }
}
