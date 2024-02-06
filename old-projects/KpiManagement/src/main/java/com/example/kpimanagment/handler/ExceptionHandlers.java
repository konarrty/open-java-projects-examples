package com.example.kpimanagment.handler;

import com.example.kpimanagment.exception.NoSuchEntityException;
import com.example.kpimanagment.exception.ServerLogicException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;
import java.util.Set;

@Slf4j
@RestControllerAdvice
public class ExceptionHandlers {

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(ServerLogicException.class)
    public String serverLogicException(HttpServletRequest req, ServerLogicException exception) {

        log.error(exception.getLocalizedMessage());

        return exception.getLocalizedMessage();
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NoSuchEntityException.class)
    public String noSuchEntityException(NoSuchEntityException exception) {

        log.error(exception.getLocalizedMessage());

        return exception.getLocalizedMessage();
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(InternalAuthenticationServiceException.class)
    public String internalAuthenticationServiceException(InternalAuthenticationServiceException exception) {

        log.error(exception.getLocalizedMessage());

        return exception.getLocalizedMessage();
    }

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    public String SQLIntegrityConstraintViolationException(SQLIntegrityConstraintViolationException exception) {

        log.error(exception.getLocalizedMessage());

        return exception.getLocalizedMessage();
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public String methodArgumentNotValidException(MethodArgumentNotValidException exception) {
        BindingResult result = exception.getBindingResult();

        List<FieldError> fieldErrors = result.getFieldErrors();
        List<ObjectError> objectErrors = result.getGlobalErrors();

        StringBuilder errorMessage = new StringBuilder();

        if (!fieldErrors.isEmpty()) {
            errorMessage.append(fieldErrors.get(0).getDefaultMessage()).append("\n");
            return errorMessage.toString();
        }

        errorMessage.append(objectErrors.get(0).getDefaultMessage()).append("\n");

        return errorMessage.toString();
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(ConstraintViolationException.class)
    public String constraintViolationException(ConstraintViolationException exception) {

        Set<ConstraintViolation<?>> violations = exception.getConstraintViolations();

        StringBuilder errorMessage = new StringBuilder();

        for (ConstraintViolation<?> violation : violations) {
            errorMessage.append(violation.getMessage()).append("\n");
        }

        return errorMessage.toString();
    }

}
