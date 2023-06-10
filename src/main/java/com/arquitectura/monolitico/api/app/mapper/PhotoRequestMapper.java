package com.arquitectura.monolitico.api.app.mapper;

import com.arquitectura.monolitico.api.app.dto.PhotoRequestDTO;
import com.arquitectura.monolitico.api.app.entity.Photo;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface PhotoRequestMapper {

    PhotoRequestMapper INSTANCE = Mappers.getMapper(PhotoRequestMapper.class);

    public Photo DTOtoEntity(PhotoRequestDTO photoRequestDTO);

    public PhotoRequestDTO EntityToDTO(Photo photo);


}
