package com.example.crud.modules.auth.dto;

import com.example.crud.modules.auth.entities.Role;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import java.io.Serializable;
import java.util.Collection;
import java.util.Date;

/**
 * A DTO for the {@link com.example.crud.modules.auth.entities.User} entity
 */
@Data
public class UserDto implements Serializable {
    @Email
    @NotNull
    @NotBlank
    private final String email;
    @NotNull
    private final String password;
    @Past
    @NotNull
    private final Date birthDay;
    private final Long[] roles;
}