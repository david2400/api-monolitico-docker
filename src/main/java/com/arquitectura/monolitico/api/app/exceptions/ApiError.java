package com.arquitectura.monolitico.api.app.exceptions;

import lombok.Getter;
import lombok.Setter;

@Getter
public class ApiError {

    private  String exceptionName;
    private  String message;

    public ApiError(String exceptionName, String message) {
        this.exceptionName = exceptionName;
        this.message = message;
    }

    public ApiError (String message){
        this.message=message;
    }
}