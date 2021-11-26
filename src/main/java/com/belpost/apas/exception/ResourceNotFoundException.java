package com.belpost.apas.exception;

public class ResourceNotFoundException extends AbstractBusinessException{
    private static final long serialVersionUID = 7633771968504954742L;

    public ResourceNotFoundException(String message) {
        super(message);
    }
}
