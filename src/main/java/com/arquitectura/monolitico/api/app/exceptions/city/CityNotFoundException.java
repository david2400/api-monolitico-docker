package com.arquitectura.monolitico.api.app.exceptions.city;

public class CityNotFoundException extends RuntimeException{

    public CityNotFoundException(Long id) {
        super(String.format("The City with id : %s does not exist", id));
    }

}
