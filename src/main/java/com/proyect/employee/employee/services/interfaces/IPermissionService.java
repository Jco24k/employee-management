package com.proyect.employee.employee.services.interfaces;

import com.proyect.employee.employee.dtos.update.UpdatePermissionDto;
import com.proyect.employee.employee.entities.Permission;
import java.util.Collection;

public interface IPermissionService {

    Collection<Permission> findAll();
    Permission findOne(Long id);
    Permission update(UpdatePermissionDto roleDto, Long id) ;
}
