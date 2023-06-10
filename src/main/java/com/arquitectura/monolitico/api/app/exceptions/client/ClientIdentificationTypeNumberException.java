package com.arquitectura.monolitico.api.app.exceptions.client;

public class ClientIdentificationTypeNumberException extends  RuntimeException{

    public ClientIdentificationTypeNumberException(String identificationType){
        super(String.format("The identification type : %s has to be numbers only",identificationType));
    }
}
