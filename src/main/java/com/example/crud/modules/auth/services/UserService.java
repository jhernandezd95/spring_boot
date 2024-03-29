package com.example.crud.modules.auth.services;

import com.example.crud.common.http_errors.NotFoundException;
import com.example.crud.common.http_errors.UnauthorizedException;
import com.example.crud.modules.auth.dto.RolesToUserDto;
import com.example.crud.modules.auth.entities.Role;
import com.example.crud.modules.auth.entities.User;
import com.example.crud.modules.auth.repositories.RoleRepository;
import com.example.crud.modules.auth.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@AllArgsConstructor
public class UserService implements UserDetailsService {

    private UserRepository userRepository;

    private RoleRepository roleRepository;

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

        if (!user.getIsEnable()) {
            throw new UnauthorizedException("Account not active");
        }

        if (user.getIsAccountNonExpired()) {
            throw new UnauthorizedException("Account expired");
        }

        if (user.getIsAccountNonLocked()) {
            throw new UnauthorizedException("Account locked");
        }

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

    public void remove(Long id) {
        User user = this.getById(id);

        userRepository.delete(user);
    }

    public User changeLockedValue(Long id) {
        User user = this.getById(id);

        user.setIsAccountNonLocked(!user.getIsAccountNonLocked());

        userRepository.save(user);

        return user;
    }

    public User changeExpiredValue(Long id) {
        User user = this.getById(id);

        user.setIsAccountNonExpired(!user.getIsAccountNonExpired());

        userRepository.save(user);

        return user;
    }

    @Transactional
    public void addRoleToUser(Long id, RolesToUserDto rolesToUserDto) {
		User user = this.getById(id);

		roleRepository.deleteAll(user.getRoles());

		List<Role> roles = roleRepository.findByIdIn(rolesToUserDto.getRolesId());

		if (roles.size() != rolesToUserDto.getRolesId().length) {
			throw new NotFoundException("Not all provided roles exist");
		}

		user.setRoles(roles);

		userRepository.save(user);
	}

	public User findByEmail(String email) {
		Optional<User> optionalUser = userRepository.findByEmail(email);

		if (optionalUser.isEmpty()) {
			throw new NotFoundException("User not found with email " + email);
		}

		return optionalUser.get();
	}
}
