package com.proyect.employee.employee.services;

import com.proyect.employee.employee.dtos.update.UpdatePermissionDto;
import com.proyect.employee.employee.entities.Permission;
import com.proyect.employee.employee.exception.ResourceNotFoundException;
import com.proyect.employee.employee.repositories.PermissionRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PermissionServiceTest {

    @Mock
    private PermissionRepository repository;
    @InjectMocks
    private PermissionService permissionService;

    @Test
    void findAll() {
        permissionService.findAll();
        verify(repository).findAll(); //verify that they call the findAll method
    }

    @Test
    void findOne() {
        Long id = 1L;
        Permission permissionStub = new Permission();
        permissionStub.setId(id);
        when(repository.findById(id)).thenReturn(Optional.of(permissionStub));
        Permission permissionFound = permissionService.findOne(id);
        assertEquals(permissionStub.getId(), permissionFound.getId());
        assertEquals(permissionStub, permissionFound);

    }

    @Test
    public void testFindOneNotFound() {
        Long id = 1L;

        when(repository.findById(id)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> permissionService.findOne(id));
    }

    @Test
    void update() {
            Long id = 1L;
            UpdatePermissionDto permitDto = new UpdatePermissionDto();
            permitDto.setDescription("New Description");

            Permission permission = new Permission();
            permission.setId(id);
            permission.setDescription("Old Description");
            when(repository.findById(id)).thenReturn(Optional.of(permission));
            when(repository.save(any(Permission.class))).thenAnswer(i -> i.getArguments()[0]);
            Permission updatedPermission = permissionService.update(permitDto, id);
            assertEquals(permitDto.getDescription(), updatedPermission.getDescription());
    }

    @Test
    void getPermits() {
    }
}