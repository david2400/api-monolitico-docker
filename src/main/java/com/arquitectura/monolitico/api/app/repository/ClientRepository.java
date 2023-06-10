package com.arquitectura.monolitico.api.app.repository;

import com.arquitectura.monolitico.api.app.entity.Client;
import com.arquitectura.monolitico.api.app.entity.IdentificationType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ClientRepository extends JpaRepository<Client,Long> {

    Optional<Client> findByIdentification(String identification);
    Optional<Client> findByMail(String mail);
    Optional<Client> findByIdentificationAndIdentificationType(String  identification, IdentificationType identificationType);

    List<Client> findByIdentificationType(IdentificationType identificationType);







}
