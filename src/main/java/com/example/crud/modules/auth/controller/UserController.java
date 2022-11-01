package com.example.crud.modules.auth.controller;

import com.example.crud.modules.auth.entities.User;
import com.example.crud.modules.auth.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import java.util.List;

@Controller
@RequestMapping(path = "/v1")
@AllArgsConstructor
public class UserController {

    private UserService userService;

    private static final String PATH = "/user";
    private static final String PATH_ONE = "/user/{userId}";

    @GetMapping(path = PATH)
    @PreAuthorize("hasAuthority('admin')")
    public @ResponseBody Iterable<User> getAllUsers() {
        List<User> users = userService.getAll();
        return users;
    }

    @GetMapping(path = PATH_ONE)
    @PreAuthorize("hasAuthority('client')")
    public @ResponseBody User getOneUser(@PathVariable @NotNull @DecimalMin("0") Long userId) {
        User users = userService.getById(userId);
        return users;
    }

    @DeleteMapping(path = PATH_ONE)
    @PreAuthorize("hasAuthority('admin')")
    public ResponseEntity removeUser(@PathVariable @NotNull @DecimalMin("0") Long userId) {
        userService.remove(userId);
        return ResponseEntity.ok().build();
    }
}
