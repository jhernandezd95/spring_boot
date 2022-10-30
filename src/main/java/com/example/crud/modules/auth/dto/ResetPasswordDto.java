package com.example.crud.modules.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@AllArgsConstructor
public class ResetPasswordDto implements Serializable {

    @NotNull
    private String pin;

    @NotNull
    @NotBlank
    private String password;
}
