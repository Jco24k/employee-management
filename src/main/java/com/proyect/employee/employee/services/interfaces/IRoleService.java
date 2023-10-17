package com.proyect.employee.employee.services.interfaces;

import com.proyect.employee.employee.dtos.create.CreateRoleDto;
import com.proyect.employee.employee.dtos.update.UpdateRoleDto;
import com.proyect.employee.employee.entities.Role;

import java.util.Collection;

public interface IRoleService {

    Collection<Role> findAll();
    Role findOne(Long id);
    Role create(CreateRoleDto roleDto) throws Exception;
    Role update(UpdateRoleDto roleDto, Long id) throws Exception;
    void delete(Long id) ;
}
