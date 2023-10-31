package com.proyect.employee.employee.dtos.response;

import java.math.BigDecimal;
import java.util.Date;

public interface SalaryLogAndUserDto {
    Long getId();
    BigDecimal getPrevious_salary();
    BigDecimal getNew_salary();
    Date getCreatedAt();
    UserDto getEmployee();
}

