package com.arquitectura.monolitico.api.app.data;

import com.arquitectura.monolitico.api.app.entity.Client;

public class ClientData {

    public static final Client client =Client.builder()
            .id(1l)
            .age("30")
            .city(CityData.cityArmenia)
            .identificationType(IdentificationTypeData.identificationTypeCedula)
            .mail("santi.posada.1321@gmail.com")
            .name("santiago")
            .lastName("posada h")
            .identification("1007730758")
            .build();
    public static final Client clientUno =Client.builder()
            .id(2l)
            .age("32")
            .city(CityData.cityBogota)
            .identificationType(IdentificationTypeData.identificationTypeCedula)
            .mail("pedro@gmail.com")
            .name("pedro")
            .lastName("ramirez")
            .identification("1077730758")
            .build();

    public static final Client clientDos =Client.builder()
            .id(3l)
            .age("20")
            .city(CityData.cityManizales)
            .identificationType(IdentificationTypeData.identificationTypeIdentidad)
            .mail("sebastian@gmail.com")
            .name("sebastian")
            .lastName("pedro ramirez")
            .identification("1777730758")
            .build();

    public static final Client clientTres =Client.builder()
            .id(4l)
            .age("20")
            .city(CityData.cityManizales)
            .identificationType(IdentificationTypeData.identificationTypeIdentidad)
            .mail("pedro@gmail.com")
            .name("pedro")
            .lastName("pedro ramirez")
            .identification("1777730756")
            .build();
}
