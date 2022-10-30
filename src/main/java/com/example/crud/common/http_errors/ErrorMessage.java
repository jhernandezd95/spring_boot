package com.example.crud.common.http_errors;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Data
class ErrorMessage {

    @NotNull
    @NotBlank
    private final String error;

    @NotNull
    @NotBlank
    private final String message;

    @NotNull
    private final Integer code;

    private final List<ErrorField> errorFields;

    ErrorMessage(Exception exception, Integer code) {
        this.error = exception.getClass().getSimpleName();
        this.message = exception.getMessage();
        this.code = code;
        this.errorFields = new ArrayList<>();
    }

    public ErrorMessage(Exception exception, Integer code, List<ErrorField> parseErrors) {
        this.error = exception.getClass().getSimpleName();
        this.message = exception.getMessage();
        this.code = code;
        this.errorFields = parseErrors;
    }
}
