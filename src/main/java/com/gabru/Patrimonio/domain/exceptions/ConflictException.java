package com.gabru.Patrimonio.domain.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.CONFLICT)
public class ConflictException extends RuntimeException {
    private static final String DESCRIPTION = "Conflict Exception (409)";

    public ConflictException(String detail) {
        super(DESCRIPTION + ". " + detail);
    }

}
