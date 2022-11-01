package com.example.crud.modules.auth.controller;

import com.example.crud.modules.auth.dto.RolesToUserDto;
import com.example.crud.modules.auth.entities.User;
import com.example.crud.modules.auth.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
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
    private static final String PATH_ONE = PATH + "/{userId}";
    private static final String PATH_IS_EXPIRED = PATH_ONE + "/expired";
    private static final String PATH_IS_LOCKED = PATH_ONE + "/locked";

    private static final String PATH_CHANGE_ROLES = PATH_ONE + "/roles";

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

    @PatchMapping(path = PATH_IS_EXPIRED)
    @PreAuthorize("hasAuthority('admin')")
    public ResponseEntity<User> changeExpiredValue(@PathVariable @NotNull @DecimalMin("0") Long userId) {
        User user = userService.changeExpiredValue(userId);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PatchMapping(path = PATH_IS_LOCKED)
    @PreAuthorize("hasAuthority('admin')")
    public ResponseEntity<User> changeLockedValue(@PathVariable @NotNull @DecimalMin("0") Long userId) {
        User user = userService.changeLockedValue(userId);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PatchMapping(path = PATH_CHANGE_ROLES)
    @PreAuthorize("hasAuthority('admin')")
    public ResponseEntity changeLockedValue(@PathVariable @NotNull @DecimalMin("0") Long userId, @Validated @RequestBody RolesToUserDto rolesToUserDto) {
        userService.addRoleToUser(userId, rolesToUserDto);
        return ResponseEntity.ok().build();
    }
}
