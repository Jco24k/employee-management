package com.proyect.employee.employee.controllers;

import com.proyect.employee.employee.config.PathController;
import com.proyect.employee.employee.dtos.create.CreateRoleDto;
import com.proyect.employee.employee.dtos.update.UpdateRoleDto;
import com.proyect.employee.employee.entities.Role;
import com.proyect.employee.employee.exception.ResourceNotFoundException;
import com.proyect.employee.employee.services.interfaces.IRoleService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping(PathController.ROLE)
@Tag(name = "Roles (roles)")
public class RoleController {

    @Autowired
    private IRoleService service;

    @GetMapping
    public ResponseEntity<Collection<Role>> findAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Role> search(@PathVariable Long id) {
        return ResponseEntity.ok(service.findOne(id));
    }

    @PostMapping
    public ResponseEntity<Role> create(@Valid @RequestBody CreateRoleDto roleDto) throws ResourceNotFoundException {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(roleDto));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Role> patch(@PathVariable Long id,
                                 @RequestBody @Validated(CreateRoleDto.ValidatedRole.class) UpdateRoleDto roleDto)
             {
        return ResponseEntity.ok(service.update(roleDto,id));

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.ok(String.format("Role with id '%s' deleted successfully", id));
    }


}
