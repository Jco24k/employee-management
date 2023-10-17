package com.proyect.employee.employee.exception;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler{

    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorDetails handleResourceNotFoundException(
            ResourceNotFoundException ex, WebRequest request) {
        return new ErrorDetails(new Date(), ex.getMessage(), request.getDescription(false));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String>  customValidationErrorHandling(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String nameField = ((FieldError) error).getField();
            String messageString = error.getDefaultMessage();
            errors.put(nameField, messageString);
        });
        return errors;
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleSQLIntegrityConstraintViolation(DataIntegrityViolationException ex) {
        return ex.getMessage();
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String handleAllUncaughtException(Exception exception) {
        return "No se pudo crear o actualizar la entidad - Verifica los registros";
    }

}