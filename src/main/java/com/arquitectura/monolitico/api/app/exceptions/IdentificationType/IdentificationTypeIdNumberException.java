package com.arquitectura.monolitico.api.app.exceptions.IdentificationType;

public class IdentificationTypeIdNumberException extends  RuntimeException{


    public IdentificationTypeIdNumberException(String idIdentificationType){
        super(String.format("The id : %s has to be numbers only",idIdentificationType));
    }


}
