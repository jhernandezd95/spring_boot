package com.example.crud.modules.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
public class ReqLoginDto implements Serializable {

    @NotNull
    @NotBlank
    private final String email;

    @NotNull
    @NotBlank
    private final String password;
}
