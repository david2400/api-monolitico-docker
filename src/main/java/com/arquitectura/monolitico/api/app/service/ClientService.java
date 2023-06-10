package com.arquitectura.monolitico.api.app.service;

import com.arquitectura.monolitico.api.app.dto.ClientDTO;
import com.arquitectura.monolitico.api.app.dto.ClientRequestDTO;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface ClientService {


    List<ClientDTO> listAllClients() ;

    ClientRequestDTO saveClient(ClientRequestDTO clientRequestDTO, MultipartFile multipartFile) throws IOException;

    String deleteClient(String identification)throws IOException;

    ClientDTO findById(String id)throws IOException;

    ClientDTO findByIdentificationAndIdentificationType(String identification, String identificationType)throws IOException;

    List<ClientDTO>  listAllClientsIdentificationType(String identificationType)throws IOException;

    ClientRequestDTO updateClient(String  identificacion,ClientRequestDTO clientRequestDTO)throws IOException;

}
