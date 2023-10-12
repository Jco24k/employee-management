package com.proyect.employee.employee.auth;

import com.proyect.employee.employee.auth.dtos.LoginResponseDto;
import com.proyect.employee.employee.auth.dtos.LoginUserDto;
import com.proyect.employee.employee.auth.interfaces.IAuthService;
import com.proyect.employee.employee.entities.User;
import com.proyect.employee.employee.exception.ResourceNotFoundException;
import com.proyect.employee.employee.repositories.UserRepository;
import com.proyect.employee.employee.security.CustomUserDetails;
import com.proyect.employee.employee.security.JwtService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService implements IAuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    @Override
    public LoginResponseDto login(LoginUserDto userDto) {
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                userDto.getUsername(), userDto.getPassword());
        Authentication authentication = authenticationManager.authenticate(usernamePasswordAuthenticationToken);
        CustomUserDetails user = (CustomUserDetails) authentication.getPrincipal();
        return LoginResponseDto.builder()
                .token(jwtService.generateToken(user))
                .id(user.getId())
                .build();
    }
}
