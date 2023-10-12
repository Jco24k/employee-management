package com.proyect.employee.employee.dtos.update;

import com.proyect.employee.employee.dtos.create.CreateRoleDto;
import com.proyect.employee.employee.dtos.create.CreateUserDto;
import com.proyect.employee.employee.dtos.decorators.interfaces.NoDuplicates;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class UpdateUserDto extends CreateUserDto {

    @Nullable
    @Size(min = 3,max = 30, groups = ValidatedUser.class)
    @Email(groups = ValidatedUser.class)
    private String username;

    @Nullable
    @Size(min = 6, max = 50, groups = ValidatedUser.class)
    @Pattern(regexp = "(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).{6,}",
            message = "The password must have a Uppercase, lowercase letter and a number ",groups = ValidatedUser.class)
    private String password;

    @Nullable
    @Size(min = 1,max = 30, groups = ValidatedUser.class)
    private String name;

    @Nullable
    private Boolean isActive;

    @Nullable
    @NoDuplicates(groups = ValidatedUser.class)
    private List<Long> roleIds = new ArrayList<>();
}
