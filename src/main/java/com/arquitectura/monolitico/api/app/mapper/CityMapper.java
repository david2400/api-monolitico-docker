package com.arquitectura.monolitico.api.app.mapper;

import com.arquitectura.monolitico.api.app.dto.CityDTO;
import com.arquitectura.monolitico.api.app.dto.IdentificationTypeDTO;
import com.arquitectura.monolitico.api.app.entity.City;
import com.arquitectura.monolitico.api.app.entity.IdentificationType;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;


@Mapper
public interface CityMapper {


    CityMapper INSTANCE = Mappers.getMapper(CityMapper.class);


    public City DTOtoEntity(CityDTO cityDTO);

    public CityDTO mapToDto(City city);

    public List<IdentificationTypeDTO> mapToDto(List<City> cities);














}
