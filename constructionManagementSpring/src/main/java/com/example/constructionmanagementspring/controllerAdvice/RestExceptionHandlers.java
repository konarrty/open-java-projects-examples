package com.example.constructionmanagementspring.controllerAdvice;

import com.example.constructionmanagementspring.dto.exceptionDto.ApiException;
import com.example.constructionmanagementspring.exception.EntityAlreadyExistException;
import com.example.constructionmanagementspring.exception.NoSuchEntityException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class RestExceptionHandlers {

    @ExceptionHandler(NoSuchEntityException.class)
    public ResponseEntity<?> onNoSuchEntityException(NoSuchEntityException exception) {

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new ApiException(exception.getMessage(),exception.getMessage()));

    }

    @ExceptionHandler(EntityAlreadyExistException.class)
    public ResponseEntity<?> onEntityAlreadyExistException(EntityAlreadyExistException exception) {

        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(new ApiException(exception.getMessage(),exception.getMessage()));

    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<?> onIllegalArgumentException(IllegalArgumentException exception) {

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ApiException(exception.getMessage(),exception.getMessage()));
    }
}
