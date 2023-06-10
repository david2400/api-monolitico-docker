package com.arquitectura.monolitico.api.app.repository;

import com.arquitectura.monolitico.api.app.entity.City;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CityRepository extends JpaRepository<City,Long> {


}
