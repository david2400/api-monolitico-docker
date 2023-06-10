package com.arquitectura.monolitico.api.app.exceptions.photo;

public class PhotNotFoundIdentificationException extends  RuntimeException{

    public PhotNotFoundIdentificationException(String identification) {
        super(String.format("the photo associated with a client with identification : %s was not found", identification));
    }
}
