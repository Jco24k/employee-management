package com.proyect.employee.employee.repositories;

import com.proyect.employee.employee.entities.Permission;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;


class PermissionRepositoryTest extends BaseRepositoryTest{

    @Autowired
    private  PermissionRepository repository;

    @Test
    void itShouldFindByIdIn() {
        List<Permission> permissions = repository.findAll();
        Set<Long> permissionIds = permissions.stream().map(Permission::getId).collect(Collectors.toSet());
        Set<Permission> permissionFound = repository.findByIdIn(permissionIds);
        assertEquals(permissions.size(),permissionFound.size());
    }

    @Test
    void itShouldFindByCode() {
        Permission permission = repository.findAll().get(0);
        Permission permissionFound = repository.findByCode(permission.getCode());
        assertEquals(permission.getCode(),permissionFound.getCode());
    }

    @Test
    void itShouldExistsByCode() {
        Permission permission = repository.findAll().get(0);
        Boolean verify = repository.existsByCode(permission.getCode());
        assertThat(verify).isTrue();
    }
}