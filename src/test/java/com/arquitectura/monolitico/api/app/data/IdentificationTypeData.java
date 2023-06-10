package com.arquitectura.monolitico.api.app.data;

import com.arquitectura.monolitico.api.app.entity.IdentificationType;

public class IdentificationTypeData {

    public static final IdentificationType identificationTypeCedula= IdentificationType.builder().id(1l).name("Cédula de Ciudadania").build();
    public static final IdentificationType identificationTypeIdentidad= IdentificationType.builder().id(2l).name("Tarjeta de Identidad").build();
}
