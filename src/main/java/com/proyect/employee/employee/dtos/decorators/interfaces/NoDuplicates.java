package com.proyect.employee.employee.dtos.decorators.interfaces;

import com.proyect.employee.employee.dtos.decorators.NoDuplicatesValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = NoDuplicatesValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface NoDuplicates {
    String message() default "The list must not contain duplicate elements";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}