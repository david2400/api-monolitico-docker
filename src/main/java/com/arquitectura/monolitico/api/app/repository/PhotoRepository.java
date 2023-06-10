package com.arquitectura.monolitico.api.app.repository;

import com.arquitectura.monolitico.api.app.entity.Photo;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PhotoRepository extends MongoRepository<Photo,String> {

    @Query(value = "{'idClient':?0}")
    Optional<Photo> findByIdClient(Long idClient);
}
