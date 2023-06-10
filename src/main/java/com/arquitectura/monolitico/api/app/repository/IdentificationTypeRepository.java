package com.arquitectura.monolitico.api.app.repository;

import com.arquitectura.monolitico.api.app.entity.IdentificationType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IdentificationTypeRepository extends JpaRepository<IdentificationType,Long> {
}
