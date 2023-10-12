package com.proyect.employee.employee.dtos.update;

import com.proyect.employee.employee.dtos.create.CreateRoleDto;
import com.proyect.employee.employee.dtos.decorators.interfaces.NoDuplicates;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class UpdateRoleDto extends CreateRoleDto{

    @Nullable
    @Size(min = 3,max = 30, groups = ValidatedRole.class)
    private String name;

    @Nullable
    private Boolean isActive;

    @Nullable
    @NoDuplicates(groups = ValidatedRole.class)
    private List<Long> permissionIds = new ArrayList<>();
}
