package com.arquitectura.monolitico.api.app.exceptions.client;

public class ClientIdNumberException extends  RuntimeException{

    public ClientIdNumberException(String identificationType){
        super(String.format("The id : %s has to be numbers only",identificationType));
    }
}
