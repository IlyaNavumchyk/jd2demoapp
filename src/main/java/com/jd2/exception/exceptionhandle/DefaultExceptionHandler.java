package com.jd2.exception.exceptionhandle;

import com.jd2.exception.NoSuchEntityException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Collections;

import static com.jd2.util.UUIDGenerator.generateUUID;

@RestControllerAdvice
public class DefaultExceptionHandler {

    @ExceptionHandler({NoSuchEntityException.class, EmptyResultDataAccessException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorContainer handleEntityNotFoundException(Exception e) {

        return ErrorContainer
                .builder()
                .exceptionId(generateUUID())
                .errorMessage(e.getMessage())
                .errorCode(2)
                .clazz(e.getClass().getSimpleName())
                .build();
    }

    @ExceptionHandler(NumberFormatException.class)
    public ResponseEntity<Object> handleNumberFormatException(Exception e) {

        ErrorContainer error = ErrorContainer
                .builder()
                .clazz(e.getClass().getSimpleName())
                .exceptionId(generateUUID())
                .errorMessage(e.getMessage())
                .errorCode(3)
                .stackTrace(e.getStackTrace())
                .build();

        return new ResponseEntity<>(Collections.singletonMap("error", error), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleException(Exception e) {

        ErrorContainer error = ErrorContainer
                .builder()
                .clazz(e.getClass().getSimpleName())
                .exceptionId(generateUUID())
                .errorMessage(e.getMessage() + " General error")
                .errorCode(1)
                .stackTrace(e.getStackTrace())
                .build();

        return new ResponseEntity<>(Collections.singletonMap("error", error), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}