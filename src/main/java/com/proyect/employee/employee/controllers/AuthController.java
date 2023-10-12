package com.proyect.employee.employee.controllers;

import com.proyect.employee.employee.auth.dtos.LoginResponseDto;
import com.proyect.employee.employee.auth.dtos.LoginUserDto;
import com.proyect.employee.employee.auth.interfaces.IAuthService;
import com.proyect.employee.employee.config.PathController;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
@RestController
@RequestMapping(PathController.AUTH)
@Tag(name = "Auth")
public class AuthController {
    @Autowired
    private IAuthService service;

    @PostMapping("login")
    public ResponseEntity<LoginResponseDto> login(@RequestBody @Valid LoginUserDto userDto) {
        return ResponseEntity.ok(service.login(userDto));
    }

}
