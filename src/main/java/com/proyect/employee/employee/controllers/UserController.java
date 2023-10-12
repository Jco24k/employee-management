package com.proyect.employee.employee.controllers;

import com.proyect.employee.employee.config.PathController;
import com.proyect.employee.employee.dtos.create.CreateUserDto;
import com.proyect.employee.employee.dtos.update.UpdateUserDto;
import com.proyect.employee.employee.entities.User;
import com.proyect.employee.employee.services.interfaces.IUserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.util.Collection;

@RestController
@RequestMapping(PathController.USER)
@Tag(name = "Users (usuarios)")
public class UserController {

    @Autowired
    private IUserService service;

    @GetMapping
    public ResponseEntity<Collection<User>> findAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> search(@PathVariable Long id) {
        return ResponseEntity.ok(service.findOne(id));
    }

    @PostMapping
    public ResponseEntity<User> create(@Valid @RequestBody CreateUserDto userDto){
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(userDto));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<User> patch(@PathVariable Long id,
                                 @RequestBody @Validated(CreateUserDto.ValidatedUser.class) UpdateUserDto roleDto)
             {
        return ResponseEntity.ok(service.update(roleDto,id));

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.ok(String.format("User with id '%s' deleted successfully", id));
    }


}
