package com.example.crud.modules.auth.services;

import com.example.crud.common.http_errors.NotFoundException;
import com.example.crud.modules.auth.entities.Role;
import com.example.crud.modules.auth.entities.User;
import com.example.crud.modules.auth.repositories.RoleRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class RoleService {

    private RoleRepository roleRepository;

    public List<Role> getAll() {
        return roleRepository.findAll();
    }

    public void remove(Long id) {
        Optional<Role> role = roleRepository.findById(id);

        if (role.isEmpty()) {
            throw new NotFoundException("Role not found with id " + id);
        }

        roleRepository.delete(role.get());
    }
}
