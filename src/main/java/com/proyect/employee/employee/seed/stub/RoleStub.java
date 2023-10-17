package com.proyect.employee.employee.seed.stub;

import com.github.javafaker.Faker;
import com.proyect.employee.employee.entities.Permission;
import com.proyect.employee.employee.entities.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class RoleStub {

    @Autowired
    private Faker faker;
    public Role getStub(Set<Permission> permissions){
        return new Role(
                faker.name().firstName(),
                permissions
        );
    }

}
