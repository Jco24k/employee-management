package com.proyect.employee.employee.mappers;

import com.proyect.employee.employee.database.model.PermissionData;
import com.proyect.employee.employee.entities.Permission;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
@Slf4j
public class PermissionDataMapper implements Function<PermissionData, Permission> {

    @Override
    public Permission apply(PermissionData permit){
        return new Permission(permit.getCode(),permit.getName(),permit.getDescription());
    }
}
