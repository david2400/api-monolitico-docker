package com.arquitectura.monolitico.api.app.service;

import com.arquitectura.monolitico.api.app.dto.ClientDTO;
import com.arquitectura.monolitico.api.app.dto.ClientRequestDTO;
import com.arquitectura.monolitico.api.app.entity.City;
import com.arquitectura.monolitico.api.app.entity.Client;
import com.arquitectura.monolitico.api.app.entity.IdentificationType;
import com.arquitectura.monolitico.api.app.entity.Photo;
import com.arquitectura.monolitico.api.app.exceptions.IdentificationType.IdentificationTypeIdNumberException;
import com.arquitectura.monolitico.api.app.exceptions.IdentificationType.IdentificationTypeNotFoundException;
import com.arquitectura.monolitico.api.app.exceptions.city.CityNotFoundException;
import com.arquitectura.monolitico.api.app.exceptions.client.*;
import com.arquitectura.monolitico.api.app.exceptions.global.GlobalDataRequiredException;
import com.arquitectura.monolitico.api.app.mapper.ClientMapper;
import com.arquitectura.monolitico.api.app.mapper.ClientRequestMapper;
import com.arquitectura.monolitico.api.app.repository.CityRepository;
import com.arquitectura.monolitico.api.app.repository.ClientRepository;
import com.arquitectura.monolitico.api.app.repository.IdentificationTypeRepository;
import com.arquitectura.monolitico.api.app.repository.PhotoRepository;
import com.arquitectura.monolitico.api.app.service.ClientService;
import lombok.AllArgsConstructor;
import org.bson.BsonBinarySubType;
import org.bson.types.Binary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ClientServiceImpl implements ClientService {

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private CityRepository cityRepository;

    @Autowired
    private PhotoRepository photoRepository;

    @Autowired
    private IdentificationTypeRepository identificationTypeRepository;

    @Override
    @Transactional()
    public ClientRequestDTO saveClient(ClientRequestDTO clientRequestDTO, MultipartFile multipartFile) throws IOException {

        if(clientRequestDTO != null){


            // verifica si existe la identificacion
            if(this.clientRepository.findByIdentification(clientRequestDTO.getIdentification()).isPresent()){
                throw new ClientDataIdentificationExistException(clientRequestDTO.getIdentification());
            }
            // verifica si existe el correo
            if(this.clientRepository.findByMail(clientRequestDTO.getMail()).isPresent()){
                throw new ClientDataMailExistException(clientRequestDTO.getMail());
            }

            // verifica si  no existe el tipo de identificacion por el id
            if(this.identificationTypeRepository.findById(clientRequestDTO.getIdentificationTypeId()).isEmpty()){
                throw  new IdentificationTypeNotFoundException(clientRequestDTO.getIdentificationTypeId());
            }
            // verifica si no existe la ciudad por el id
            if(this.cityRepository.findById(clientRequestDTO.getCityId()).isEmpty()){
                throw  new CityNotFoundException(clientRequestDTO.getCityId());
            }

            int age=Integer.parseInt(clientRequestDTO.getAge());

            int minAge=18;
            int maxAge=130;
            if(age < minAge || age > maxAge){
                throw new ClientAgeException();
            }

            if (multipartFile.isEmpty()){
                return ClientRequestMapper.INSTANCE.EntityToDTO(this.clientRepository.save(ClientRequestMapper.INSTANCE.DTOtoEntity(clientRequestDTO)));
            }

            Photo photo =new Photo();
            Client client= this.clientRepository.save(ClientRequestMapper.INSTANCE.DTOtoEntity(clientRequestDTO));
            photo.setIdClient(client.getId());
            // Pasa el multipartFile a Binary
            Binary imageBinary= new Binary(BsonBinarySubType.BINARY, multipartFile.getBytes());
            photo.setBase64(Base64.getEncoder().encodeToString(imageBinary.getData()));

            this.photoRepository.save(photo);


            return ClientRequestMapper.INSTANCE.EntityToDTO(client);



        }else{
            throw  new GlobalDataRequiredException();

        }



    }

    @Override
    @Transactional()
    public ClientRequestDTO updateClient(String identification,ClientRequestDTO clientRequestDTO) throws IOException {

        if(clientRequestDTO != null && identification !=null){
            Client clientFound = this.clientRepository.findByIdentification(identification).orElseThrow(()-> new ClientNotFoundIdentificationException(identification));
            //verifica si se modifico la identificacion
            if(!clientRequestDTO.getIdentification().equals(clientFound.getIdentification())){
                // verifica si existe la identificacion
                if(this.clientRepository.findByIdentification(clientRequestDTO.getIdentification()).isPresent()){
                    throw new ClientDataIdentificationExistException(clientRequestDTO.getIdentification());
                }
            }
            //varifica si se modifico el correo
            if (!clientRequestDTO.getMail().equals(clientFound.getMail())){
                // verifica si existe el correo
                if(this.clientRepository.findByMail(clientRequestDTO.getMail()).isPresent()){
                    throw new ClientDataMailExistException(clientRequestDTO.getMail());
                }
            }
            // verifica si  existe el tipo de identificacion por el id
            if(this.identificationTypeRepository.findById(clientRequestDTO.getIdentificationTypeId()).isEmpty()){
                throw new IdentificationTypeNotFoundException(clientRequestDTO.getIdentificationTypeId());
            }
            // verifica si  existe la ciudad por el id
            if(this.cityRepository.findById(clientRequestDTO.getCityId()).isEmpty()){
                throw new CityNotFoundException(clientRequestDTO.getCityId());
            }
            int age=Integer.parseInt(clientRequestDTO.getAge());
            int minAge=18;
            int maxAge=130;
            if(age < minAge || age > maxAge){
                throw new ClientAgeException();
            }
            // lo hago creando una entidad , ya que si modifico el identificador (id) de  city de clientFound aparece este error,JpaSystemException  identifier of an instance altered from X to Y
            City city =new City();
            city.setId(clientRequestDTO.getCityId());
            IdentificationType identificationType=new IdentificationType();
            identificationType.setId(clientRequestDTO.getIdentificationTypeId());
            clientFound.setName(clientRequestDTO.getName());
            clientFound.setLastName(clientRequestDTO.getLastName());
            clientFound.setAge(clientRequestDTO.getAge());
            clientFound.setIdentification(clientRequestDTO.getIdentification());
            clientFound.setMail(clientRequestDTO.getMail());
            clientFound.setCity(city);
            clientFound.setIdentificationType(identificationType);
            return  ClientRequestMapper.INSTANCE.EntityToDTO(this.clientRepository.save(clientFound));
        }else {
            throw new GlobalDataRequiredException();
        }
    }
    @Override
    @Transactional()
    public String deleteClient(String identification) throws IOException{
        Client client = this.clientRepository.findByIdentification(identification).orElseThrow(()-> new ClientNotFoundIdentificationException(identification));
        Optional<Photo> photo = this.photoRepository.findByIdClient(client.getId());
        this.clientRepository.delete(client);
        if (photo.isEmpty()){
            return "removed client with document "+client.getIdentification();
        }else{
            this.photoRepository.delete(photo.get());
            return "removed client and the photo with document "+client.getIdentification();
        }
    }
    @Override
    @Transactional(readOnly=true)
    public List<ClientDTO> listAllClients() {
        return ClientMapper.INSTANCE.mapToDto(this.clientRepository.findAll());
    }

    @Override
    @Transactional(readOnly=true)
    public ClientDTO findById(String id) throws IOException {

        if(id != null){
            Long idLong;
            try {
                idLong = Long.parseLong(id);
            }catch (NumberFormatException ex){
                throw  new ClientIdNumberException (id);
            }
            return ClientMapper.INSTANCE.mapToDto(this.clientRepository.findById(idLong).orElseThrow(()->new ClientNotFoundIdException(idLong)));
        }else{
            throw  new GlobalDataRequiredException();
        }
    }

    @Override
    @Transactional(readOnly=true)
    public ClientDTO findByIdentificationAndIdentificationType(String identification, String identificationType) throws IOException{
        if ( identification != null && identificationType !=null){

            try {

                IdentificationType identification_type = new IdentificationType();
                identification_type.setId(Long.parseLong(identificationType));

                return ClientMapper.INSTANCE.mapToDto(this.clientRepository.findByIdentificationAndIdentificationType(identification, identification_type).orElseThrow(() -> new ClientNotFoundIdenTypeException(identification, Long.parseLong(identificationType))));
            }catch (NumberFormatException ex){
                throw  new ClientIdentificationTypeNumberException(identificationType);
            }

        }else{
            throw  new GlobalDataRequiredException();
        }
    }

    @Override
    @Transactional(readOnly=true)
    public List<ClientDTO> listAllClientsIdentificationType(String idIdentificationType) throws IOException{
        if(idIdentificationType != null){
            long idLong;
            try {
                idLong = Long.parseLong(idIdentificationType);
            }catch (NumberFormatException ex){
                throw  new IdentificationTypeIdNumberException(idIdentificationType);
            }
            IdentificationType identification_type = new IdentificationType();
            identification_type.setId(idLong);
            return ClientMapper.INSTANCE.mapToDto(this.clientRepository.findByIdentificationType(identification_type));
        }else {
            throw  new GlobalDataRequiredException();
        }
    }


}
