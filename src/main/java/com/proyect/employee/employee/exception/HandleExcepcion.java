package com.proyect.employee.employee.exception;

import org.springframework.dao.DataIntegrityViolationException;

public class HandleExcepcion {

    public static void SqlError(DataIntegrityViolationException ex, String nameEntity) throws Exception {
        if (ex.getMessage().contains("Duplicate")) {
            throw new DataIntegrityViolationException("User already exists on the db: "+ ex.getRootCause());
        }
        throw new InternalError(String.format("Could not create or update entity of type %s - Check logs",nameEntity));
    }
}
