package com.arquitectura.monolitico.api.app.service;

import com.arquitectura.monolitico.api.app.dto.PhotoDTO;
import com.arquitectura.monolitico.api.app.dto.PhotoRequestDTO;
import com.arquitectura.monolitico.api.app.entity.Client;
import com.arquitectura.monolitico.api.app.entity.Photo;
import com.arquitectura.monolitico.api.app.exceptions.client.ClientNotFoundIdentificationException;
import com.arquitectura.monolitico.api.app.exceptions.global.GlobalDataRequiredException;
import com.arquitectura.monolitico.api.app.exceptions.photo.PhotNotFoundIdentificationException;
import com.arquitectura.monolitico.api.app.exceptions.photo.PhotoClientExistException;
import com.arquitectura.monolitico.api.app.exceptions.photo.PhotoNotFoundIdException;
import com.arquitectura.monolitico.api.app.exceptions.photo.PhotoNotFoundUpdateException;
import com.arquitectura.monolitico.api.app.mapper.PhotoMapper;
import com.arquitectura.monolitico.api.app.mapper.PhotoRequestMapper;
import com.arquitectura.monolitico.api.app.repository.ClientRepository;
import com.arquitectura.monolitico.api.app.repository.PhotoRepository;
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
public class PhotoServiceImpl implements PhotoService {

    @Autowired
    private PhotoRepository photoRepository;

    @Autowired
    private ClientRepository clientRepository;

    @Override
    @Transactional(readOnly=true)
    public List<PhotoDTO> listAllPhotos() {
        return PhotoMapper.INSTANCE.mapToDto(this.photoRepository.findAll());
    }

    @Override
    @Transactional(readOnly=true)
    public PhotoDTO getPhotoByClientIdentification(String identification) throws IOException {

        if(identification != null) {

            Client client = this.clientRepository.findByIdentification(identification).orElseThrow(()-> new ClientNotFoundIdentificationException(identification));

            Photo photo = this.photoRepository.findByIdClient(client.getId()).orElseThrow(()-> new PhotNotFoundIdentificationException(identification));

            return PhotoMapper.INSTANCE.mapToDto(photo);

        }else {
            throw  new GlobalDataRequiredException();
        }

    }

    @Override
    @Transactional(readOnly=true)
    public PhotoDTO getPhotoById(String id) throws IOException {

        if (id != null){

            return PhotoMapper.INSTANCE.mapToDto(this.photoRepository.findById(id)
                    .orElseThrow(()-> new PhotoNotFoundIdException(id)));

        }else{
            throw  new GlobalDataRequiredException();
        }


    }

    @Override
    @Transactional
    public PhotoRequestDTO save( MultipartFile multipartFile, String identification) throws IOException{
        if( !multipartFile.isEmpty() && identification !=null){

            Client client = this.clientRepository.findByIdentification(identification).orElseThrow(()-> new ClientNotFoundIdentificationException(identification));


            //valida si el cliente no tiene una foto
            if(this.photoRepository.findByIdClient(client.getId()).isPresent()){
                throw new PhotoClientExistException(identification);
            }

            Photo photo = new Photo();
            photo.setIdClient(client.getId());
            photo.setBase64(Base64.getEncoder().encodeToString( new Binary(BsonBinarySubType.BINARY, multipartFile.getBytes()).getData()));

            return PhotoRequestMapper.INSTANCE.EntityToDTO(this.photoRepository.save(photo));

        }else{
            throw new GlobalDataRequiredException();
        }
    }

    @Override
    @Transactional
    public String deleteById(String idPhoto) throws IOException {

        if (idPhoto != null){

            Photo photo = this.photoRepository.findById(idPhoto).orElseThrow(()->new PhotoNotFoundIdException(idPhoto));

            this.photoRepository.delete(photo);
            return "removed photo with id "+photo.getId();

        }else{
            throw new GlobalDataRequiredException();
        }

    }

    @Override
    @Transactional
    public String deleteByIdentification(String identification) throws IOException {

        if (identification != null){
            Client client = this.clientRepository.findByIdentification(identification).orElseThrow(()-> new ClientNotFoundIdentificationException(identification));
            Photo photo = this.photoRepository.findByIdClient(client.getId()).orElseThrow(()->new PhotoNotFoundIdException(identification));
            this.photoRepository.delete(photo);
            return "photo with id "+photo.getId() + " was removed";

        }else{
            throw new GlobalDataRequiredException();
        }

    }

    @Override
    @Transactional
    public PhotoRequestDTO update(MultipartFile multipartFile, String identification) throws IOException {
        if( !multipartFile.isEmpty() && identification !=null){

            Client client = this.clientRepository.findByIdentification(identification).orElseThrow(()->new ClientNotFoundIdentificationException(identification));
            //valida si el cliente tiene una foto
            Photo photoFound= this.photoRepository.findByIdClient(client.getId()).orElseThrow(PhotoNotFoundUpdateException::new);

            Photo photo = new Photo();
            photo.setId(photoFound.getId());
            photo.setIdClient(photoFound.getIdClient());
                //Binary imageBinary= new Binary(BsonBinarySubType.BINARY, multipartFile.getBytes());
            photo.setBase64(Base64.getEncoder().encodeToString(new Binary(BsonBinarySubType.BINARY, multipartFile.getBytes()).getData()));
            PhotoRequestDTO photoRequestDTO=PhotoRequestMapper.INSTANCE.EntityToDTO(this.photoRepository.save(photo));
            return photoRequestDTO;
        }else{
            throw new GlobalDataRequiredException();
        }
    }


}
