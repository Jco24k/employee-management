package com.proyect.employee.employee.services;

import com.proyect.employee.employee.dtos.create.CreateUserDto;
import com.proyect.employee.employee.dtos.update.UpdateUserDto;
import com.proyect.employee.employee.entities.User;
import com.proyect.employee.employee.exception.HandleExcepcion;
import com.proyect.employee.employee.exception.ResourceNotFoundException;
import com.proyect.employee.employee.mappers.MapperNotNull;
import com.proyect.employee.employee.mappers.UserDtoMapper;
import com.proyect.employee.employee.repositories.UserRepository;
import com.proyect.employee.employee.services.interfaces.IUserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.SerializationUtils;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;

@Service
@Slf4j
public class UserService implements IUserService {

    @Autowired
    private UserRepository repository;

    @Autowired
    private RoleService roleService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private SalaryLogService salaryLogService;

    @Autowired
    private UserDtoMapper userDtoMapper;

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
        getAndVerifyDto(userDto,user,false);
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
        getAndVerifyDto(userDto,userFound,true);
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
        PropertyMap<User, User> propertyMap = new PropertyMap<>() {
                    protected void configure() {
                        skip().setRoles(null);
                        skip().setSalary(null);
                        skip().setIsActive(null);
                    }
                };
        modelMapper.addMappings(propertyMap);
        return modelMapper;
    }

    private void getAndVerifyDto(CreateUserDto userDto,User userEntity, Boolean isUpdate) throws Exception {
        modelMapperWithoutFks().map(userDtoMapper.apply(userDto), userEntity);
        if(userDto.getSalary() != null){
            BigDecimal oldSalary = Optional.ofNullable(userEntity.getSalary())
                        .orElse(BigDecimal.ZERO)
                        .setScale(2, RoundingMode.HALF_UP);
            BigDecimal newSalary = userDto.getSalary().setScale(2, RoundingMode.HALF_UP);
            if(!oldSalary.equals(newSalary) && !oldSalary.equals(BigDecimal.ZERO) && isUpdate){
                salaryLogService.create(oldSalary,newSalary,userEntity.getId());
            }
            userEntity.setSalary(userDto.getSalary());
        }
        if(!userDto.getRoleIds().isEmpty()){
            userEntity.getRoles().clear();
            userEntity.setRoles(roleService.getRoles(new HashSet<>(userDto.getRoleIds())));
        }
        if(userDto.getPassword()!=null){
            String password = userEntity.getPassword();
            userEntity.setPassword(passwordEncoder.encode(password));
        }
        if(isUpdate){
            UpdateUserDto request = (UpdateUserDto) userDto;
            if(request.getIsActive()!=null){
                userEntity.setIsActive(request.getIsActive());
            }
        }
    }

}
