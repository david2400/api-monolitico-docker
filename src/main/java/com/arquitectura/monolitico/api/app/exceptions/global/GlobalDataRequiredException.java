package com.arquitectura.monolitico.api.app.exceptions.global;

public class GlobalDataRequiredException extends  RuntimeException{

    public GlobalDataRequiredException (){
        super(String.format("enter all the required data"));
    }




}
