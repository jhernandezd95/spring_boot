package com.example.crud.modules.auth.services;

import com.example.crud.modules.auth.entities.Role;
import com.example.crud.modules.auth.repositories.RoleRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class RoleService {

    private RoleRepository roleRepository;

    public List<Role> getAll() {
        return roleRepository.findAll();
    }
}
