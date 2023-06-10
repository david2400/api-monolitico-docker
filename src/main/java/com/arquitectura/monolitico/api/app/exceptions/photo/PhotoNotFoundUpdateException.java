package com.arquitectura.monolitico.api.app.exceptions.photo;

public class PhotoNotFoundUpdateException extends RuntimeException{

    public PhotoNotFoundUpdateException() {
        super(String.format("you cannot modify a client's photo if it has not yet been saved"));
    }


}
