package com.proyect.employee.employee.auth.interfaces;

import com.proyect.employee.employee.auth.dtos.LoginResponseDto;
import com.proyect.employee.employee.auth.dtos.LoginUserDto;

public interface IAuthService {
    LoginResponseDto login(LoginUserDto userDto);
}
