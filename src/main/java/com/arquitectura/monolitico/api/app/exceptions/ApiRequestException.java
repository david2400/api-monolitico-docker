package com.arquitectura.monolitico.api.app.exceptions;

public class ApiRequestException extends RuntimeException{

    public ApiRequestException(String message) {

        super(message);
    }
}