package com.arquitectura.monolitico.api.app.exceptions;


import com.arquitectura.monolitico.api.app.exceptions.IdentificationType.IdentificationTypeNotFoundException;
import com.arquitectura.monolitico.api.app.exceptions.photo.PhotoClientExistException;
import com.arquitectura.monolitico.api.app.exceptions.IdentificationType.IdentificationTypeIdNumberException;
import com.arquitectura.monolitico.api.app.exceptions.city.CityNotFoundException;
import com.arquitectura.monolitico.api.app.exceptions.client.*;
import com.arquitectura.monolitico.api.app.exceptions.global.GlobalDataRequiredException;
import com.arquitectura.monolitico.api.app.exceptions.photo.PhotNotFoundIdentificationException;
import com.arquitectura.monolitico.api.app.exceptions.photo.PhotoNotFoundIdException;
import com.arquitectura.monolitico.api.app.exceptions.photo.PhotoNotFoundUpdateException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolationException;
import java.util.concurrent.ConcurrentHashMap;

@ControllerAdvice
public class CustomRestExceptionHandler extends ResponseEntityExceptionHandler {

    private static final String ERROR_OCCURRED_CONTACT_ADMIN = "An error occurred, please contact administrator.";
    private static final ConcurrentHashMap<String, Integer> STATUS_CODE = new ConcurrentHashMap<>();

    public CustomRestExceptionHandler() {

        STATUS_CODE.put(ApiRequestException.class.getSimpleName(), HttpStatus.BAD_REQUEST.value());
        STATUS_CODE.put(ClientNotFoundIdentificationException.class.getSimpleName(),HttpStatus.NOT_FOUND.value());
        STATUS_CODE.put(ClientNotFoundIdException.class.getSimpleName(),HttpStatus.NOT_FOUND.value());
        STATUS_CODE.put(ClientNotFoundIdenTypeException.class.getSimpleName(),HttpStatus.NOT_FOUND.value());
        STATUS_CODE.put(ClientAgeException.class.getSimpleName(),HttpStatus.BAD_REQUEST.value());
        STATUS_CODE.put(ClientDataMailExistException.class.getSimpleName(),HttpStatus.CONFLICT.value());
        STATUS_CODE.put(ClientIdentificationTypeNumberException.class.getSimpleName(),HttpStatus.CONFLICT.value());
        STATUS_CODE.put(ClientIdNumberException.class.getSimpleName(),HttpStatus.CONFLICT.value());
        STATUS_CODE.put(GlobalDataRequiredException.class.getSimpleName(),HttpStatus.CONFLICT.value());
        STATUS_CODE.put(ClientDataIdentificationExistException.class.getSimpleName(),HttpStatus.CONFLICT.value());
        STATUS_CODE.put(CityNotFoundException.class.getSimpleName(),HttpStatus.NOT_FOUND.value());
        STATUS_CODE.put(IdentificationTypeNotFoundException.class.getSimpleName(),HttpStatus.NOT_FOUND.value());
        STATUS_CODE.put(IdentificationTypeIdNumberException.class.getSimpleName(),HttpStatus.CONFLICT.value());
        STATUS_CODE.put(PhotNotFoundIdentificationException.class.getSimpleName(),HttpStatus.NOT_FOUND.value());
        STATUS_CODE.put(PhotoNotFoundIdException.class.getSimpleName(),HttpStatus.NOT_FOUND.value());
        STATUS_CODE.put(PhotoNotFoundUpdateException.class.getSimpleName(),HttpStatus.NOT_FOUND.value());
        STATUS_CODE.put(PhotoClientExistException.class.getSimpleName(),HttpStatus.CONFLICT.value());

    }



    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ApiError> handleConstraintViolationException(ConstraintViolationException exception) {



        String exceptionName = exception.getClass().getSimpleName();
        String message= exception.getMessage();
        ApiError error = new ApiError(exceptionName,message);
        return new ResponseEntity<>(error,HttpStatus.BAD_REQUEST);

    }



    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> handleApiRequestException(Exception e) {

        ResponseEntity<ApiError> result;
        String exceptionName = e.getClass().getSimpleName();
        String message = e.getMessage();
        Integer code = STATUS_CODE.get(exceptionName);

        //e.printStackTrace();

        if(code !=null){
            ApiError error = new ApiError(exceptionName,message);
            result = new ResponseEntity<>(error,HttpStatus.valueOf(code));
            //e.printStackTrace();
        }else{
            ApiError error = new ApiError(exceptionName,ERROR_OCCURRED_CONTACT_ADMIN);
            result = new ResponseEntity<>(error,HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return result;
    }
}