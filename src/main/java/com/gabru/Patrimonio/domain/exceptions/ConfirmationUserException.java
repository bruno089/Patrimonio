package com.gabru.Patrimonio.domain.exceptions;

public class ConfirmationUserException extends ConflictException  {

    private static final String DESCRIPTION = "Confirmation Exception";

    public ConfirmationUserException(String detail) {
        super(DESCRIPTION + ". " + detail);
    }
}
