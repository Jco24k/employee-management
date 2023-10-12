package com.proyect.employee.employee.dtos.decorators;

import com.proyect.employee.employee.dtos.decorators.interfaces.NoDuplicates;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.List;

public class NoDuplicatesValidator implements ConstraintValidator<NoDuplicates, List<Long>> {

    @Override
    public void initialize(NoDuplicates constraintAnnotation) {
    }

    @Override
    public boolean isValid(List<Long> list, ConstraintValidatorContext context) {
        if (list == null) {
            return true;
        }

        return list.stream().distinct().count() == list.size();
    }
}