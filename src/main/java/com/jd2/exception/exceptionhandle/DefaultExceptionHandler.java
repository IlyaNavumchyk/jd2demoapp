package com.jd2.exception.exceptionhandle;

import com.jd2.exception.NoSuchEntityException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Collections;

import static com.jd2.util.UUIDGenerator.generateUUID;

@RestControllerAdvice
public class DefaultExceptionHandler {

    private static final String ERROR = "error";

    @ExceptionHandler({NoSuchEntityException.class, EmptyResultDataAccessException.class})
    public ResponseEntity<Object> handleEntityNotFoundException(Exception e) {

        ErrorContainer error = ErrorContainer
                .builder()
                .exceptionId(generateUUID())
                .errorMessage(e.getMessage())
                .errorCode(2)
                .clazz(e.getClass().getSimpleName())
                .build();

        return new ResponseEntity<>(Collections.singletonMap(ERROR, error), HttpStatus.NOT_FOUND);
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

        return new ResponseEntity<>(Collections.singletonMap(ERROR, error), HttpStatus.BAD_REQUEST);
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

        return new ResponseEntity<>(Collections.singletonMap(ERROR, error), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}