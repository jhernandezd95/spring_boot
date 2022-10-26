package com.example.crud.modules.auth.controller;

import com.example.crud.modules.auth.dto.UserDto;
import com.example.crud.modules.auth.entities.User;
import com.example.crud.modules.auth.services.AuthServices;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping(path = "/v1/auth")
@AllArgsConstructor
public class AuthController {

    private AuthServices authServices;

    @PostMapping(path = "/register")
    public @ResponseBody User getAllUsers(@Validated @RequestBody UserDto userDto) {
        User user = authServices.register(userDto);
        return user;
    }
}
