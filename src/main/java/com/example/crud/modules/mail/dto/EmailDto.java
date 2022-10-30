package com.example.crud.modules.mail.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class EmailDto {

    @NotNull
    @NotBlank
    private String recipient;

    @NotNull
    @NotBlank
    private String body;

    @NotNull
    @NotBlank
    private String subject;

    @NotBlank
    private String attachment;

    public EmailDto(String email, String body, String subject) {
        this.recipient = email;
        this.body = body;
        this.subject = subject;
    }
}
