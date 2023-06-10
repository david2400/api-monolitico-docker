package com.arquitectura.monolitico.api.app.exceptions.IdentificationType;

public class IdentificationTypeNotFoundException extends  RuntimeException {

    public IdentificationTypeNotFoundException(Long id) {
        super(String.format("The Identification Type with id : %s does not exist", id));
    }



}
