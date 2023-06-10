package com.arquitectura.monolitico.api.app.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClientDTO {


    private Long id;


    private String name;


    private String lastName;


    private String age;


    private String identification;


    private String mail;


    private CityDTO city;


    private IdentificationTypeDTO identificationType;

}
