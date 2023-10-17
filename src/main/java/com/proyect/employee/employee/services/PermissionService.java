package com.proyect.employee.employee.services;

import com.proyect.employee.employee.dtos.update.UpdatePermissionDto;
import com.proyect.employee.employee.entities.Permission;
import com.proyect.employee.employee.exception.HandleExcepcion;
import com.proyect.employee.employee.exception.ResourceNotFoundException;
import com.proyect.employee.employee.mappers.MapperNotNull;
import com.proyect.employee.employee.repositories.PermissionRepository;
import com.proyect.employee.employee.services.interfaces.IPermissionService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Collection;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class PermissionService implements IPermissionService {

    private final PermissionRepository repository;
    private final String nameEntity = "Permit";

    @Override
    @Transactional(readOnly = true)
    public Collection<Permission> findAll() {
        return repository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Permission findOne(Long id) {
        return repository.findById(id).orElseThrow(
                ()-> new ResourceNotFoundException("Permit not found with id: " + id)
        );
    }

    @Override
    @Transactional()
    public Permission update(UpdatePermissionDto permitDto, Long id) throws Exception {
        Permission permissionFound = findOne(id);
        MapperNotNull.notNullMapper().map(permitDto, permissionFound);
        try{
            return repository.save(permissionFound);

        }catch (DataIntegrityViolationException ex){
            HandleExcepcion.SqlError(ex,nameEntity);
            return null;
        }
    }
    public Set<Permission> getPermits(Set<Long> permitIds)  {
        Set<Permission> permissions = repository.findByIdIn(permitIds);
        if (permissions.size() != permitIds.size()) {
            List<Long> notFoundPermits = permitIds.stream()
                    .filter(permitId -> permissions.stream().noneMatch(pert -> pert.getId().equals(permitId)))
                    .toList();
            throw new ResourceNotFoundException("Some Permits were not found"+notFoundPermits);
        }
        return permissions;
    }

}
