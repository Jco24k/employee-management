package com.proyect.employee.employee.repositories;

import com.proyect.employee.employee.entities.Role;
import com.proyect.employee.employee.seed.stub.RoleStub;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import static org.junit.jupiter.api.Assertions.*;

@Import(RoleStub.class)
class RoleRepositoryTest extends BaseRepositoryTest {

    @Autowired
    private RoleRepository repository;
    @Autowired
    private RoleStub roleStub;
    @Test
    void findByName() {
        Role newRole = repository.save(roleStub.getStub(null));
        Role roleFound = repository.findByName(newRole.getName());
        assertNotNull(roleFound);
        assertEquals(newRole.getName(),roleFound.getName());
    }

    @Test
    void findByIdIn() {
        List<Role> roles = repository.findAll();
        Set<Long> roleIds = roles.stream().map(Role::getId).collect(Collectors.toSet());
        Set<Role> roleFounds = repository.findByIdIn(roleIds);
        assertEquals(roles.size(),roleFounds.size());
    }
}