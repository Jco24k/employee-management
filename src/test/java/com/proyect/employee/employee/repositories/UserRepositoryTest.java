package com.proyect.employee.employee.repositories;

import com.proyect.employee.employee.config.SecurityConfig;
import com.proyect.employee.employee.config.TestConfig;
import com.proyect.employee.employee.entities.User;
import com.proyect.employee.employee.seed.stub.UserStub;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@Import({UserStub.class, TestConfig.class})
class UserRepositoryTest extends BaseRepositoryTest {

    @Autowired
    private UserRepository repository;
    @Autowired
    private UserStub userStub;

    @Test
    void findByUsername() {
        User newUser = repository.save(userStub.getStub(null));
        User userFound = repository.findByUsername(newUser.getUsername());
        assertNotNull(userFound);
        assertEquals(newUser.getName(),userFound.getName());
    }
}