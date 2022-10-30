package com.example.crud.modules.auth.repositories;

import com.example.crud.modules.auth.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByEmail(String name);

    List<User> findAllByLastLoginBeforeAndIsAccountNonExpired(Date limit, Boolean expired);
}
