package com.proyect.employee.employee.services;

import com.proyect.employee.employee.dtos.create.CreateUserDto;
import com.proyect.employee.employee.dtos.update.UpdateUserDto;
import com.proyect.employee.employee.entities.User;
import com.proyect.employee.employee.exception.HandleExcepcion;
import com.proyect.employee.employee.exception.ResourceNotFoundException;
import com.proyect.employee.employee.mappers.MapperNotNull;
import com.proyect.employee.employee.repositories.UserRepository;
import com.proyect.employee.employee.services.interfaces.IUserService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.Collection;
import java.util.HashSet;

@Service
@Slf4j
public class UserService implements IUserService {

    @Autowired
    private UserRepository repository;

    @Autowired
    private RoleService roleService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private final String nameEntity = "User";
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
    public User create(CreateUserDto userDto) throws Exception{
        User user = new User();
        getAndVerifyDto(userDto,user);
        try{
            return repository.save(user);

        }catch (DataIntegrityViolationException ex){
            HandleExcepcion.SqlError(ex,nameEntity);
            return null;
        }
    }

    @Override
    @Transactional()
    public User update(UpdateUserDto userDto, Long id) throws Exception {
        User userFound = findOne(id);
        getAndVerifyDto(userDto,userFound);
        try{
            return repository.save(userFound);

        }catch (DataIntegrityViolationException ex){
            HandleExcepcion.SqlError(ex,nameEntity);
            return null;
        }
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
        if(userDto.getPassword()!=null){
            String password = userEntity.getPassword();
            userEntity.setPassword(passwordEncoder.encode(password));
        }
    }

}
