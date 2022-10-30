package com.example.crud.modules.auth.services;

import com.example.crud.common.http_errors.NotFoundException;
import com.example.crud.modules.auth.entities.User;
import com.example.crud.modules.auth.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@AllArgsConstructor
public class UserService implements UserDetailsService {

    private UserRepository userRepository;

    public List<User> getAll() {
        return userRepository.findAll();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> optional = userRepository.findByEmail(username);

        if (optional.isEmpty()) {
            throw new NotFoundException("User not found with email " + username);
        }

        User user = optional.get();

        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        user.getRoles().forEach(role -> authorities.add(new SimpleGrantedAuthority(role.getName())));
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), authorities);
    }

    public User getById(Long userId) {
        Optional<User> user = userRepository.findById(userId);

        if (user.isEmpty()) {
            throw new NotFoundException("User not found with id " + userId);
        }

        return user.get();
    }

    public void expireAccounts(int days) {
        Calendar limit = Calendar.getInstance();
        limit.setTime(new Date()); // Using today's date
        limit.add(Calendar.DATE, days); // Adding x days
        List<User> users = userRepository.findAllByLastLoginBeforeAndIsAccountNonExpired(limit.getTime(), false);
        users.forEach(user -> {
            user.setIsAccountNonExpired(true);
        });
        userRepository.saveAll(users);
    }
}
