package com.proyect.employee.employee.repositories;

import com.proyect.employee.employee.entities.Permission;
import com.proyect.employee.employee.entities.enums.EPermission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Set;

@Repository
public interface PermissionRepository extends JpaRepository<Permission, Long> {
    Set<Permission> findByIdIn(Set<Long> permitIds);
    Permission findByCode(EPermission code);
    boolean existsByCode(EPermission code);
}
