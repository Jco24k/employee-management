package com.proyect.employee.employee.services;

import com.proyect.employee.employee.dtos.create.CreateRoleDto;
import com.proyect.employee.employee.dtos.update.UpdateRoleDto;
import com.proyect.employee.employee.entities.Permission;
import com.proyect.employee.employee.entities.Role;
import com.proyect.employee.employee.exception.ResourceNotFoundException;
import com.proyect.employee.employee.mappers.MapperNotNull;
import com.proyect.employee.employee.repositories.RoleRepository;
import com.proyect.employee.employee.services.interfaces.IRoleService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@Slf4j
public class RoleService implements IRoleService {

    @Autowired
    private RoleRepository repository;
    @Autowired
    private PermissionService permissionService;

    @Override
    @Transactional(readOnly = true)
    public Collection<Role> findAll() {
        return repository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Role findOne(Long id){
        return repository.findById(id).orElseThrow(
                ()-> new ResourceNotFoundException("Role not found with id: " + id)
        );
    }

    @Override
    @Transactional()
    public Role create(CreateRoleDto roleDto) {
        Role role = new Role();
        getAndVerifyDto(roleDto,role);
        return repository.save(role);
    }

    @Override
    @Transactional()
    public Role update(UpdateRoleDto roleDto, Long id) {
        Role roleFound = findOne(id);
        getAndVerifyDto(roleDto,roleFound);
        return repository.save(roleFound);
    }

    @Override
    public void delete(Long id) {
        Role roleFound = findOne(id);
        roleFound.setIsActive(false);
        repository.save(roleFound);
    }

    private ModelMapper modelMapperWithoutFks() {
        ModelMapper modelMapper = MapperNotNull.notNullMapper();
         PropertyMap<CreateRoleDto, Role> propertyMap = new
         PropertyMap<CreateRoleDto, Role>() {
         protected void configure() {
            skip().setPermissions(null);
            }
         };
        modelMapper.addMappings(propertyMap);
        return modelMapper;
    }

    private void getAndVerifyDto(CreateRoleDto roleDto,Role roleEntity){
        modelMapperWithoutFks().map(roleDto, roleEntity);
        if(!roleDto.getPermissionIds().isEmpty()){
            roleEntity.getPermissions().clear();
            roleEntity.setPermissions(permissionService.getPermits(new HashSet<>(roleDto.getPermissionIds())));
        }
    }

    public Set<Role> getRoles(Set<Long> roleIds)  {
        Set<Role> roles = repository.findByIdIn(roleIds);
        if (roles.size() != roleIds.size()) {
            List<Long> notFoundRoles = roleIds.stream()
                    .filter(roleId -> roles.stream().noneMatch(roleFound -> roleFound.getId().equals(roleId)))
                    .toList();
            throw new ResourceNotFoundException("Some Roles were not found" + notFoundRoles);
        }
        return roles;
    }

}
