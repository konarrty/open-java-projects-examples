package com.example.tourmanagement.handler;

import com.example.tourmanagement.dto.exception.ApiException;
import com.example.tourmanagement.exception.LogicException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.NoSuchElementException;
import java.util.Set;

@RestControllerAdvice
public class ApiExceptionsHandler {

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(value = {NoSuchElementException.class})
    private ApiException noSuchElementException(RuntimeException e) {

        return new ApiException(e.getClass().getSimpleName(), e.getLocalizedMessage());
    }

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(value = {LogicException.class, DataIntegrityViolationException.class})
    private ApiException logicException(RuntimeException e) {

        return new ApiException(e.getClass().getSimpleName(), e.getLocalizedMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = {ConstraintViolationException.class})
    private ApiException constraintViolationException(ConstraintViolationException e) {

        Set<ConstraintViolation<?>> violations = e.getConstraintViolations();

        StringBuilder errorMessage = new StringBuilder();

        for (ConstraintViolation<?> violation : violations) {
            errorMessage.append(violation.getMessage()).append("\n");
        }

        return new ApiException(e.getClass().getSimpleName(), errorMessage.toString());
    }

}
