package com.arquitectura.monolitico.api.app.mapper;

import com.arquitectura.monolitico.api.app.dto.ClientDTO;
import com.arquitectura.monolitico.api.app.dto.ClientRequestDTO;
import com.arquitectura.monolitico.api.app.entity.Client;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ClientRequestMapper {

    ClientRequestMapper INSTANCE = Mappers.getMapper(ClientRequestMapper.class);

     @Mapping(source = "cityId",target = "city.id")
     @Mapping(source = "identificationTypeId",target = "identificationType.id")
    public Client DTOtoEntity(ClientRequestDTO clientRequestDTO);


    @Mapping(source = "city.id",target = "cityId")
    @Mapping(source = "identificationType.id",target = "identificationTypeId")
    public ClientRequestDTO EntityToDTO(Client client);

}
