package com.proyect.employee.employee.seed.stub;

import com.github.javafaker.Faker;
import com.proyect.employee.employee.entities.Role;
import com.proyect.employee.employee.entities.User;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Set;

@Service
public class UserStub {

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private Faker faker;
    public static final String DEFAULT_PASSWORD = "Contraseña@987";
    public User getStub(String username,String password,Set<Role> roles){
        return new User(
                username,
                passwordEncoder.encode(password),
                faker.name().firstName(),
                faker.name().lastName(),
                roles,
                new BigDecimal(faker.number().randomNumber())
        );
    }

    public  User getStub(Set<Role> roles){
        return new User(
                faker.idNumber().toString().concat(
                faker.internet().emailAddress()).substring(0,30),
                passwordEncoder.encode(DEFAULT_PASSWORD),
                faker.name().firstName(),
                faker.name().lastName(),
                roles,
                 new BigDecimal(faker.number().randomNumber())
        );
    }
}
