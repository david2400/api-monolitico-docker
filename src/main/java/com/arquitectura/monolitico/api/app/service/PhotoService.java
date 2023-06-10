package com.arquitectura.monolitico.api.app.service;

import com.arquitectura.monolitico.api.app.dto.PhotoDTO;
import com.arquitectura.monolitico.api.app.dto.PhotoRequestDTO;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface PhotoService {

    List<PhotoDTO> listAllPhotos();

    PhotoDTO getPhotoByClientIdentification(String identification) throws IOException;

    PhotoDTO getPhotoById(String id) throws  IOException;

    PhotoRequestDTO save( MultipartFile multipartFile, String identification) throws IOException;

    String deleteById(String idPhoto) throws  IOException;

    String deleteByIdentification(String identification)throws  IOException;

    PhotoRequestDTO update (MultipartFile multipartFile, String identification) throws IOException;



}
