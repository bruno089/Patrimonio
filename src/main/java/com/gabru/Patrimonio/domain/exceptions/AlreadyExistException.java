package com.gabru.Patrimonio.domain.exceptions;
public class AlreadyExistException extends ConflictException{
    private static final String DESCRIPTION = "Already Exist";
    public AlreadyExistException(String detail) {
        super(DESCRIPTION + ". " +  detail);
    }
}
