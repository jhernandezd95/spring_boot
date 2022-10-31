package com.example.crud.modules.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
public class PinDto {

    @NotNull
    @NotBlank
    private String pin;

    @NotNull
    @NotBlank
    private String type;

    @NotNull
    @NotBlank
    private String email;

    public PinDto(String type, String email) {
        this.type = type;
        this.email = email;
    }

}
