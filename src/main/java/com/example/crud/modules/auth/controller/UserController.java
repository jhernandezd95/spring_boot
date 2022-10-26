package com.example.crud.modules.auth.controller;

import com.example.crud.modules.auth.entities.User;
import com.example.crud.modules.auth.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping(path = "/v1/user")
@AllArgsConstructor
public class UserController {

    private UserService userService;

    @GetMapping(path = "/")
    public @ResponseBody Iterable<User> getAllUsers() {
        List<User> users = userService.getAll();
        return users;
    }
}
