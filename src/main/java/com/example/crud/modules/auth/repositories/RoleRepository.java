package com.example.crud.modules.auth.repositories;

import com.example.crud.modules.auth.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
}