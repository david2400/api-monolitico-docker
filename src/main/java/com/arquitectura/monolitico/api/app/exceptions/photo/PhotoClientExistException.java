package com.arquitectura.monolitico.api.app.exceptions.photo;

public class PhotoClientExistException extends  RuntimeException{


    public PhotoClientExistException(String identification) {
        super(String.format("the client whit identification number : %s already has a photo", identification));
    }



}
