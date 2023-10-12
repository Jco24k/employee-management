package com.proyect.employee.employee.dtos.create;

import com.proyect.employee.employee.dtos.decorators.interfaces.NoDuplicates;
import jakarta.annotation.Nullable;
import jakarta.validation.GroupSequence;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Null;
import jakarta.validation.constraints.Size;
import jakarta.validation.groups.Default;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;


@Getter
@Setter
@GroupSequence({ CreateRoleDto.class,CreateRoleDto.ValidatedRole.class })
public class CreateRoleDto {

    @Size(min = 3,max = 30)
    @NotBlank(message = "name must not be null")
    private String name;

    @Nullable()
    @NoDuplicates()
    private List<@Min(1)Long> permissionIds = new ArrayList<>();
    public interface ValidatedRole {
    }
}
