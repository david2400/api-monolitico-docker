package com.arquitectura.monolitico.api.app.mapper;

import com.arquitectura.monolitico.api.app.dto.PhotoDTO;
import com.arquitectura.monolitico.api.app.entity.Photo;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface PhotoMapper {

    PhotoMapper INSTANCE = Mappers.getMapper(PhotoMapper.class);


    public Photo DTOtoEntity(PhotoDTO photoDTO);

    public PhotoDTO mapToDto(Photo photo);

    public List<PhotoDTO> mapToDto(List<Photo> photos);




}
