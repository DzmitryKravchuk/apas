package com.belpost.apas.exception;

public class ResourceNotFoundException extends AbstractBusinessException{
    public ResourceNotFoundException(String message) {
        super(message);
    }
}
