package com.proyect.employee.employee.database;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.proyect.employee.employee.config.EnvironmentConfig;
import com.proyect.employee.employee.database.model.PermissionData;
import com.proyect.employee.employee.entities.Permission;
import com.proyect.employee.employee.entities.Role;
import com.proyect.employee.employee.entities.User;
import com.proyect.employee.employee.entities.enums.EPermission;
import com.proyect.employee.employee.mappers.PermissionDataMapper;
import com.proyect.employee.employee.repositories.PermissionRepository;
import com.proyect.employee.employee.repositories.RoleRepository;
import com.proyect.employee.employee.repositories.UserRepository;
import com.proyect.employee.employee.seed.UserStub;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;

@Service
@Slf4j
@RequiredArgsConstructor
public class InitDatabase implements InitializingBean {

    PermissionData[] permitJson;

    private final PermissionRepository permissionRepository;
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final PermissionDataMapper permissionDataMapper;
    private final ResourceLoader resourceLoader;
    private final EnvironmentConfig environmentConfig;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void afterPropertiesSet() throws Exception {
        chargePermitData();
        validatePermit();
        registerPermits();
        initUser();
    }

    public void validatePermit(){
        Set<String> idData = new HashSet<>();
        int cantValidPermit = EPermission.values().length;
        if(permitJson.length != cantValidPermit){
            throw new RuntimeException(String.format("The files do not contain the same number of permissions valid-permit => %d | permit => %d", cantValidPermit, permitJson.length));
        }
        for (PermissionData permit : permitJson) {
            String code = permit.getCode();
            if(EPermission.contains(code)){
                if (idData.contains(code))throw new RuntimeException("Permissions are repeated");
                idData.add(code);
            }else {
                throw new RuntimeException("Permit not found with code '" + permit.getCode() + " - " + permit.getName() + "'");
            }
        }
        log.info("Permissions are correct");

    }

    private void chargePermitData() throws IOException {
        Resource resource = resourceLoader.getResource("classpath:"+environmentConfig.getPath_permission());
        InputStream inputStream = resource.getInputStream();
        ObjectMapper objectMapper = new ObjectMapper();
        permitJson = objectMapper.readValue(inputStream, PermissionData[].class);
    }

    private void registerPermits(){
        List<Permission> permissionData = Arrays.stream(permitJson).map(permissionDataMapper).toList();
        List<Permission> savePermission =  new ArrayList<>();
        permissionData.forEach(permit -> {
            if (!permissionRepository.existsByCode(permit.getCode())) {
                savePermission.add(permit);
            }
        });
        permissionRepository.saveAll(savePermission);
        log.info("Permissions registered successfully");
    }


    @Transactional
    private void initUser(){
        Permission permission = permissionRepository.findByCode(EPermission.SUPER_ADMIN);
        String roleAdmin = environmentConfig.getRole_admin();
        String userAdmin = environmentConfig.getUser_admin();
        String passAdmin = environmentConfig.getPass_admin();

        Role role = roleRepository.findByName(roleAdmin);
        if(role == null){
            role = roleRepository.save(new Role(roleAdmin,Set.of(permission)));
        }
        User user = userRepository.findByUsername(userAdmin);
        if(user == null){
            userRepository.save(UserStub.getStub(userAdmin,passwordEncoder.encode(passAdmin),Set.of(role)));
        }
        log.info("UserAdmin registered successfully");
    }
}
