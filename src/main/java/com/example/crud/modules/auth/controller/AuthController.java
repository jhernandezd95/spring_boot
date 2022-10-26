package com.example.crud.modules.auth.controller;

import com.example.crud.modules.auth.dto.ReqLoginDto;
import com.example.crud.modules.auth.dto.ResLoginDto;
import com.example.crud.modules.auth.dto.UserDto;
import com.example.crud.modules.auth.entities.User;
import com.example.crud.modules.auth.services.AuthService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(path = "/v1/auth")
@AllArgsConstructor
public class AuthController {

    private AuthService authService;

    @PostMapping(path = "/register")
    public @ResponseBody User getAllUsers(@Validated @RequestBody UserDto userDto) {
        User user = authService.register(userDto);
        return user;
    }

    @PostMapping(path = "/login")
    public @ResponseBody ResLoginDto login(@Validated @RequestBody ReqLoginDto loginDto) {
        ResLoginDto response = authService.login(loginDto);
        return response;
    }
}
