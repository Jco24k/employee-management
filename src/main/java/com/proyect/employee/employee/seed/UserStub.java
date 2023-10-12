package com.proyect.employee.employee.seed;

import com.github.javafaker.Faker;
import com.proyect.employee.employee.entities.Role;
import com.proyect.employee.employee.entities.User;

import java.util.Set;

public class UserStub {
    public static final String DEFAULT_PASSWORD = "Contraseña@987";
    public static User getStub(String username,String password,Set<Role> roles){
        Faker faker = new Faker();
        return new User(
                username,
                password,
                faker.name().firstName(),
                faker.name().lastName(),
                roles
        );
    }

    public static User getStub(Set<Role> roles){
        Faker faker = new Faker();
        return new User(
                faker.internet().emailAddress(),
                DEFAULT_PASSWORD,
                faker.name().firstName(),
                faker.name().lastName(),
                roles
        );
    }
}
