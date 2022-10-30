package com.example.crud.common.http_errors;

public class JwtException extends UnauthorizedException {

    private static final String DESCRIPTION = "Jwt error";

    public JwtException(String detail) {
        super(DESCRIPTION + ". " + detail);
    }
}
