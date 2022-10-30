package com.example.crud.modules.auth.services;

import static com.example.crud.common.utils.Constants.PIN_FORGOT;
import static com.example.crud.common.utils.Constants.PIN_ACTIVE_ACCOUNT;

import com.example.crud.common.http_errors.NotFoundException;
import com.example.crud.common.http_errors.UnauthorizedException;
import com.example.crud.common.services.JwtService;
import com.example.crud.modules.auth.dto.*;
import com.example.crud.modules.auth.entities.Pin;
import com.example.crud.modules.auth.entities.Role;
import com.example.crud.modules.auth.entities.User;
import com.example.crud.modules.auth.repositories.RoleRepository;
import com.example.crud.modules.auth.repositories.UserRepository;
import com.example.crud.modules.mail.dto.EmailDto;
import com.example.crud.modules.mail.service.EmailService;
import lombok.AllArgsConstructor;
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
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class AuthService {

    private AuthenticationManager authenticationManager;
    private UserRepository userRepository;
    private RoleRepository roleRepository;

    private JwtService jwtService;

    private PinService pinService;

    private EmailService emailService;

    @Transactional
    public User register(UserDto userDto) {
        Collection<Role> roles = this.getRoles(userDto.getRoles());

        User user = new User(userDto, roles);
        user.setPassword(new BCryptPasswordEncoder().encode(userDto.getPassword()));

        userRepository.save(user);


        Pin pin = pinService.createPin(new PinDto(PIN_ACTIVE_ACCOUNT, user.getEmail()));
        EmailDto emailDto = new EmailDto(
                user.getEmail(),
                "Para activar su cuenta envie el codigo " + pin.getPin(),
                "Activacion de cuenta"
        );
        emailService.sendSimpleMail(emailDto);
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

    public ResLoginDto login(ReqLoginDto loginDto) {

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

    private User updateLastLogin(String email) {
        Optional<User> user = userRepository.findByEmail(email);

        if (user.isEmpty()) {
            throw new NotFoundException("User not found with email " + email);
        }

        user.get().setLastLogin(new Date());
        userRepository.save(user.get());

        return user.get();
    }

    @Transactional
    public void activeUser(ActiveUserDto activeUserDto) {
        PinDto pinDto = new PinDto(activeUserDto.getPin(), PIN_ACTIVE_ACCOUNT);
        Optional<Pin> pin = pinService.checkPin(pinDto);

        if (pin.isPresent()) {
            Optional<User> user = userRepository.findByEmail(activeUserDto.getEmail());
            if (user.isEmpty()) {
                throw new NotFoundException("User not found with email " + activeUserDto.getEmail());
            }
            user.get().setIsEnable(true);
            userRepository.save(user.get());
        } else {
            throw new UnauthorizedException("Not found");
        }
    }

    public void resetPassword(ResetPasswordDto resetPasswordDto) {
        Optional<Pin> pin = pinService.checkPin(new PinDto(resetPasswordDto.getPin(), PIN_FORGOT));

        if (pin.isPresent()) {
            Optional<User> user = userRepository.findByEmail(pin.get().getEmail());
            if (user.isEmpty()) {
                throw new NotFoundException("User not found with email " + pin.get().getEmail());
            }
            String newPassword = new BCryptPasswordEncoder().encode(resetPasswordDto.getPassword());
            user.get().setPassword(newPassword);
            userRepository.save(user.get());
        } else {
            throw new NotFoundException("Code not found");
        }
    }

    public void forgot(ForgotDto forgotDto) {
        Optional<User> user = userRepository.findByEmail(forgotDto.getEmail());
        if (user.isEmpty()) {
            throw new NotFoundException("User not found with email " + forgotDto.getEmail());
        }

        Pin pin = pinService.createPin(new PinDto(PIN_FORGOT, forgotDto.getEmail()));
        EmailDto emailDto = new EmailDto(
                forgotDto.getEmail(),
                "Para reestablecer su contrasena ingrese el codigo " + pin.getPin(),
                "Olvido de contrasena"
        );
        emailService.sendSimpleMail(emailDto);
    }
}
