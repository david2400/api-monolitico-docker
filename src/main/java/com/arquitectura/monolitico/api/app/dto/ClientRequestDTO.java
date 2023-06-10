package com.arquitectura.monolitico.api.app.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.*;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ClientRequestDTO {

    @NotBlank(message = "name cannot be null")
    private String name;

    @NotBlank(message = "last name cannot be null")
    private String lastName;

    @NotBlank(message = "age cannot be null")
    @Pattern(regexp = "^((\\d)|(\\d{2})|(\\d{3}))?$", message = "you must enter only numbers")
    @Length(min = 1,max = 3,message = "the minimum age is 1 and the maximum is 130")
    private String age;

    @NotBlank(message = "identification cannot be null")
    @Pattern(regexp = "^((\\d{7})|(\\d{10})|(\\d{11})|(\\d{6}-\\d{5}))?$", message = "The identification must be exactly 7 or 10 or 11 digits as well as 6 digits separated by an underscore followed by 5 digits. example '1234567' or '12345678910' or '123456789101' '123456-12345'")
    private String  identification;

    @NotBlank(message = "mail cannot be null")
    @Pattern(regexp = "^[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?$", message = "Use the implementation of the Official Standard: RFC 5322 . Example: 'someone@someplace.es'")
    private String mail;

    @NotNull(message = "city id cannot be null")
    private Long cityId;

    @NotNull(message = "identification type id cannot be null")
    private Long identificationTypeId;


}
