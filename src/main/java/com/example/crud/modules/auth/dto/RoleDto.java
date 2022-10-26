package com.example.crud.modules.auth.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * A DTO for the {@link com.example.crud.modules.auth.entities.Role} entity
 */
@Data
public class RoleDto implements Serializable {
    @NotNull
    @NotBlank
    private final String name;
    private final String description;
}