package com.proyect.employee.employee.services.interfaces;

import com.proyect.employee.employee.dtos.create.CreateUserDto;
import com.proyect.employee.employee.dtos.update.UpdateUserDto;
import com.proyect.employee.employee.entities.User;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.Collection;

public interface IUserService {

    Collection<User> findAll();
    User findOne(Long id);
    User create(CreateUserDto userDto) throws Exception;
    User update(UpdateUserDto userDto, Long id) throws Exception;
    void delete(Long id) ;
}
