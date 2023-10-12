package com.proyect.employee.employee.services.interfaces;

import com.proyect.employee.employee.dtos.create.CreateUserDto;
import com.proyect.employee.employee.dtos.update.UpdateUserDto;
import com.proyect.employee.employee.entities.User;

import java.util.Collection;

public interface IUserService {

    Collection<User> findAll();
    User findOne(Long id);
    User create(CreateUserDto userDto) ;
    User update(UpdateUserDto userDto, Long id) ;
    void delete(Long id) ;
}
