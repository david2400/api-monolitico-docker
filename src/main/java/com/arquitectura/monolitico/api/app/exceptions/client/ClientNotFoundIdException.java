package com.arquitectura.monolitico.api.app.exceptions.client;

public class ClientNotFoundIdException extends  RuntimeException{


    public ClientNotFoundIdException(Long id) {
        super(String.format("client with ID: %s not found.", id));
    }
}
