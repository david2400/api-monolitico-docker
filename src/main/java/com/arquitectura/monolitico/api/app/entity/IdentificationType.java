package com.arquitectura.monolitico.api.app.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;


@Entity
@Table
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class IdentificationType implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column( nullable = false, length = 45,unique = true)
    private String name;

    @Column( length = 254)
    private String description;


}