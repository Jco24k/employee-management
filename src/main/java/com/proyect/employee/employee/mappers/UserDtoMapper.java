package com.proyect.employee.employee.mappers;

import com.proyect.employee.employee.dtos.create.CreateUserDto;
import com.proyect.employee.employee.entities.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.util.function.Function;

@Service
@Slf4j
public class UserDtoMapper implements Function<CreateUserDto, User> {
    @Override
    public User apply(CreateUserDto userDto){
        return User.builder()
                .username(userDto.getUsername())
                .password(userDto.getPassword())
                .name(userDto.getName())
                .lastName(userDto.getLastName())
                .build();
    }
}
