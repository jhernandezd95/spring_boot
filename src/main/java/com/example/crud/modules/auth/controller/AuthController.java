package com.example.crud.modules.auth.controller;

import com.example.crud.modules.auth.dto.*;
import com.example.crud.modules.auth.entities.User;
import com.example.crud.modules.auth.services.AuthService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(path = "/v1/auth")
@AllArgsConstructor
public class AuthController {

    private AuthService authService;

    @PostMapping(path = "/register")
    public @ResponseBody User registerUsers(@Validated @RequestBody UserDto userDto) {
        User user = authService.register(userDto);
        return user;
    }

    @PostMapping(path = "/login")
    public @ResponseBody ResLoginDto login(@Validated @RequestBody ReqLoginDto loginDto) {
        ResLoginDto response = authService.login(loginDto);
        return response;
    }

    @PostMapping(path = "/active-account")
    public ResponseEntity activeUser(@Validated @RequestBody ActiveUserDto activeUserDto) {
        authService.activeUser(activeUserDto);
        return ResponseEntity.ok().build();
    }

    @PostMapping(path = "/forgot")
    public ResponseEntity forgot(@Validated @RequestBody ForgotDto forgotDto) {
        authService.forgot(forgotDto);
        return ResponseEntity.ok().build();
    }

    @PatchMapping(path = "/reset-password")
    public ResponseEntity forgot(@Validated @RequestBody ResetPasswordDto resetPasswordDto) {
        authService.resetPassword(resetPasswordDto);
        return ResponseEntity.ok().build();
    }
}
