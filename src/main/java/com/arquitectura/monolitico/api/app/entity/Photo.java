package com.arquitectura.monolitico.api.app.entity;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.persistence.Id;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "photo")
public class Photo {

    @Id
    private String id;

    @Field
    private Long idClient;

    @Field
    private String base64;



}
