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
public class Client implements Serializable {


    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column( nullable = false, length = 45)
    private String name;

    @Column( nullable = false, length = 45)
    private String lastName;

    @Column( nullable = false,length = 3)
    private String age;

    @Column(nullable = false, length = 20,unique = true)
    private String identification;

    @Column( nullable = false, length = 100,unique = true)
    private String mail;

    @ManyToOne()
    @JoinColumn(name = "id_city")
    private City city;

    @ManyToOne()
    @JoinColumn(name = "id_identification_type")
    private IdentificationType identificationType;


}