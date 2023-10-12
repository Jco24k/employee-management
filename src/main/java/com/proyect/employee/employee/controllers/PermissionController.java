package com.proyect.employee.employee.controllers;

import com.proyect.employee.employee.config.PathController;
import com.proyect.employee.employee.dtos.update.UpdatePermissionDto;
import com.proyect.employee.employee.entities.Permission;
import com.proyect.employee.employee.services.interfaces.IPermissionService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Collection;

@RestController
@RequestMapping(PathController.PERMISSION)
@Tag(name = "Permissions (permisos)")
public class PermissionController {

    @Autowired
    private IPermissionService service;

    @GetMapping
    public ResponseEntity<Collection<Permission>> findAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Permission> search(@PathVariable Long id) {
        return ResponseEntity.ok(service.findOne(id));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Permission> patch(@PathVariable Long id,
                                 @RequestBody @Valid UpdatePermissionDto permissionDto)
             {
        return ResponseEntity.ok(service.update(permissionDto,id));

    }




}
