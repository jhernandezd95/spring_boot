package com.example.crud.common.http_errors;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class ErrorField {

    @NotNull
    private String field;

    @NotNull
    private String message;

    public ErrorField(String field, String defaultMessage) {
        this.field = field;
        this.message = defaultMessage;
    }
}
