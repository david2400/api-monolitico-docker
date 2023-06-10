package com.arquitectura.monolitico.api.app.mapper;


import com.arquitectura.monolitico.api.app.dto.IdentificationTypeDTO;
import com.arquitectura.monolitico.api.app.dto.PhotoDTO;
import com.arquitectura.monolitico.api.app.entity.IdentificationType;
import com.arquitectura.monolitico.api.app.entity.Photo;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface IdentificationTypeMapper {


    IdentificationTypeMapper INSTANCE = Mappers.getMapper(IdentificationTypeMapper.class);


    public IdentificationType DTOtoEntity(IdentificationTypeDTO identificationTypeDTO);

    public IdentificationTypeDTO mapToDto(IdentificationType identificationType);

    public List<IdentificationTypeDTO> mapToDto(List<IdentificationType> identificationType);








}
