package com.example.crud.modules.auth.services;

import com.example.crud.common.http_errors.NotFoundException;
import com.example.crud.common.services.JwtService;
import com.example.crud.modules.auth.dto.ReqLoginDto;
import com.example.crud.modules.auth.dto.ResLoginDto;
import com.example.crud.modules.auth.dto.UserDto;
import com.example.crud.modules.auth.entities.Role;
import com.example.crud.modules.auth.entities.User;
import com.example.crud.modules.auth.repositories.RoleRepository;
import com.example.crud.modules.auth.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class AuthService {

    private AuthenticationManager authenticationManager;
    private UserRepository userRepository;
    private RoleRepository roleRepository;

    private JwtService jwtService;

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
                throw new NotFoundException("Role not found with ids " + Arrays.toString(ids));
            }
            return role.get();
        }).collect(Collectors.toList());
    }

    public ResLoginDto login(@NotNull ReqLoginDto loginDto) {

        try {
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(loginDto.getEmail(), loginDto.getPassword());
            Authentication auth = authenticationManager.authenticate(authentication);

            String token = jwtService.createToken(authentication.getName(), auth.getAuthorities());

            User user = updateLastLogin(auth.getName());

            return new ResLoginDto(user, token);
        } catch (Exception error) {
            throw error;
        }
    }

    private @NotNull User updateLastLogin(String email) {
        User user = userRepository.findByEmail(email);
        user.setLastLogin(new Date());
        userRepository.save(user);

        return user;
    }
}
