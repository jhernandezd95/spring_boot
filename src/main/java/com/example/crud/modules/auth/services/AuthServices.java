package com.example.crud.modules.auth.services;

import com.example.crud.modules.auth.dto.UserDto;
import com.example.crud.modules.auth.entities.Role;
import com.example.crud.modules.auth.entities.User;
import com.example.crud.modules.auth.repositories.RoleRepository;
import com.example.crud.modules.auth.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class AuthServices {

    private UserRepository userRepository;
    private RoleRepository roleRepository;

    public User register(UserDto userDto) {
        Collection<Role> roles = this.getRoles(userDto.getRoles());

        User user = new User(userDto, roles);
        user.setPassword(new BCryptPasswordEncoder().encode(userDto.getPassword()));

        userRepository.save(user);
        return user;
    }

    private Collection<Role> getRoles(Long[] ids) {

        return Arrays.stream(ids).map(id -> {
            Optional<Role> role = roleRepository.findById(id);

            if (role.isEmpty()) {
                System.out.println("Role not found");
            }
            return role.get();
        }).collect(Collectors.toList());
    }
}
