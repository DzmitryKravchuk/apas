package com.belpost.apas.exception;

public class ArgumentMismatchException extends AbstractBusinessException{
    private static final long serialVersionUID = 6445824401912535833L;

    public ArgumentMismatchException(String message) {
        super(message);
    }
}
