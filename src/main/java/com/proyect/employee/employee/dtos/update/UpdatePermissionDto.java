package com.proyect.employee.employee.dtos.update;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdatePermissionDto {

    @Nullable
    @Size(max = 120)
    private String description;
}
