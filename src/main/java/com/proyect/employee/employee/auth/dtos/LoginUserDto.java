package com.proyect.employee.employee.auth.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginUserDto {

    @Size(min = 3,max = 30)
    @NotBlank(message = "name must not be null")
    @Email()
    private String username;

    @NotBlank(message = "password must not be null")
    @Size(min = 6, max = 50)
    @Pattern(regexp = "(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).{6,}",
            message = "The password must have a Uppercase, lowercase letter and a number ")
    private String password;
}
