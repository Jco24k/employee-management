package com.proyect.employee.employee.auth.dtos;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class LoginResponseDto {
    private Long id;
    private String token;
}
