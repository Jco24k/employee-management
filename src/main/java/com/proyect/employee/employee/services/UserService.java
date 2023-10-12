package com.proyect.employee.employee.services;

import com.proyect.employee.employee.dtos.create.CreateUserDto;
import com.proyect.employee.employee.dtos.update.UpdateUserDto;
import com.proyect.employee.employee.entities.User;
import com.proyect.employee.employee.exception.ResourceNotFoundException;
import com.proyect.employee.employee.mappers.MapperNotNull;
import com.proyect.employee.employee.repositories.UserRepository;
import com.proyect.employee.employee.services.interfaces.IUserService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.HashSet;

@Service
@Slf4j
public class UserService implements IUserService {

    @Autowired
    private UserRepository repository;

    @Autowired
    private RoleService roleService;

    @Override
    @Transactional(readOnly = true)
    public Collection<User> findAll() {
        return repository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public User findOne(Long id){
        return repository.findById(id).orElseThrow(
                ()-> new ResourceNotFoundException("User not found with id: " + id)
        );
    }

    @Override
    @Transactional()
    public User create(CreateUserDto userDto) {
        User user = new User();
        getAndVerifyDto(userDto,user);
        return repository.save(user);
    }

    @Override
    @Transactional()
    public User update(UpdateUserDto userDto, Long id) {
        User userFound = findOne(id);
        getAndVerifyDto(userDto,userFound);
        return repository.save(userFound);
    }

    @Override
    public void delete(Long id) {
        User userFound = findOne(id);
        userFound.setIsActive(false);
        repository.save(userFound);
    }

    private ModelMapper modelMapperWithoutFks() {
        ModelMapper modelMapper = MapperNotNull.notNullMapper();
        PropertyMap<CreateUserDto, User> propertyMap = new PropertyMap<>() {
                    protected void configure() {
                        skip().setRoles(null);
                    }
                };
        modelMapper.addMappings(propertyMap);
        return modelMapper;
    }

    private void getAndVerifyDto(CreateUserDto userDto,User userEntity){
        modelMapperWithoutFks().map(userDto, userEntity);
        if(!userDto.getRoleIds().isEmpty()){
            userEntity.getRoles().clear();
            userEntity.setRoles(roleService.getRoles(new HashSet<>(userDto.getRoleIds())));
        }
    }

}
