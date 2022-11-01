package com.example.crud.modules.auth.controller;

import com.example.crud.modules.auth.entities.Role;
import com.example.crud.modules.auth.services.RoleService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import java.util.List;

@Controller
@AllArgsConstructor
@RequestMapping(path = "/v1")
@PreAuthorize("hasAuthority('admin')")
public class RoleController {

    private RoleService roleService;

    private static final String PATH = "/role";
    private static final String PATH_ONE = "/role/{roleId}";

    @GetMapping(path = PATH)
    public @ResponseBody Iterable<Role> getAllRoles() {
        List<Role> roles = roleService.getAll();
        return roles;
    }

    @DeleteMapping(path = PATH_ONE)
    @PreAuthorize("hasAuthority('admin')")
    public ResponseEntity removeUser(@PathVariable @NotNull @DecimalMin("0") Long roleId) {
        roleService.remove(roleId);
        return ResponseEntity.ok().build();
    }
}
