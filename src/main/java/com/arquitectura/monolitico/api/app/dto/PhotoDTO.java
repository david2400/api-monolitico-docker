package com.arquitectura.monolitico.api.app.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor

public class PhotoDTO {

    private String id;


    private Long idClient;


    private String base64;


}
