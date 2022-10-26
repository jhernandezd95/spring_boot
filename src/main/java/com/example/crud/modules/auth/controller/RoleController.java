package com.example.crud.modules.auth.controller;

import com.example.crud.modules.auth.entities.Role;
import com.example.crud.modules.auth.services.RoleService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@AllArgsConstructor
@RequestMapping(path = "/v1/role")
public class RoleController {

    private RoleService roleService;

    @GetMapping(path = "/")
    public @ResponseBody Iterable<Role> getAllUsers() {
        List<Role> roles = roleService.getAll();
        return roles;
    }
}
