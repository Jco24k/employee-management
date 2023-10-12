package com.proyect.employee.employee.repositories;

import com.proyect.employee.employee.entities.Permission;
import com.proyect.employee.employee.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;


@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByName(String name);
    Set<Role> findByIdIn(Set<Long> roleIds);
}
