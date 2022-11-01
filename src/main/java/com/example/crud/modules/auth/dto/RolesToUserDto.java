package com.example.crud.modules.auth.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class RolesToUserDto implements Serializable {

    private Long[] rolesId;
}
