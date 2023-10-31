package com.proyect.employee.employee.controllers;

import com.proyect.employee.employee.auth.dtos.LoginResponseDto;
import com.proyect.employee.employee.auth.dtos.LoginUserDto;
import com.proyect.employee.employee.auth.interfaces.IAuthService;
import com.proyect.employee.employee.config.PathController;
import com.proyect.employee.employee.dtos.response.SalaryLogAndUserDto;
import com.proyect.employee.employee.entities.SalaryLog;
import com.proyect.employee.employee.services.SalaryLogService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.hibernate.sql.ast.tree.expression.Collation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping(PathController.LOGS)
@Tag(name = "Logs")
public class LogController {
    @Autowired
    private SalaryLogService service;

    @GetMapping("salary")
    public ResponseEntity<Collection<SalaryLogAndUserDto>> findAllSalariesWithUsers() {
        return ResponseEntity.ok(service.findAllSalariesWithUsers());
    }

}
