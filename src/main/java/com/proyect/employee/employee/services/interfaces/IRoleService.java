package com.proyect.employee.employee.services.interfaces;

import com.proyect.employee.employee.dtos.create.CreateRoleDto;
import com.proyect.employee.employee.dtos.update.UpdateRoleDto;
import com.proyect.employee.employee.entities.Role;

import java.util.Collection;

public interface IRoleService {

    Collection<Role> findAll();
    Role findOne(Long id);
    Role create(CreateRoleDto roleDto) ;
    Role update(UpdateRoleDto roleDto, Long id) ;
    void delete(Long id) ;
}
