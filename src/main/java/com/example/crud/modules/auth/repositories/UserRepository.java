package com.example.crud.modules.auth.repositories;

import com.example.crud.modules.auth.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByEmail(String name);
}
