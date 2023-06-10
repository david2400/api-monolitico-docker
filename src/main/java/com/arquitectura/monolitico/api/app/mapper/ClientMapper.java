package com.arquitectura.monolitico.api.app.mapper;

import com.arquitectura.monolitico.api.app.dto.ClientDTO;
import com.arquitectura.monolitico.api.app.entity.Client;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface ClientMapper {

    ClientMapper INSTANCE = Mappers.getMapper(ClientMapper.class);

    public Client DTOtoEntity(ClientDTO clientDTO);

    public ClientDTO mapToDto(Client client);

    public List<ClientDTO> mapToDto(List<Client> clients);





}
