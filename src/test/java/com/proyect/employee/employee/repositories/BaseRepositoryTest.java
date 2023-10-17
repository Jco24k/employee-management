package com.proyect.employee.employee.repositories;

import com.github.javafaker.Faker;
import jakarta.transaction.Transactional;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

@DataJpaTest
@Transactional
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Import(Faker.class)
public class BaseRepositoryTest {
}
