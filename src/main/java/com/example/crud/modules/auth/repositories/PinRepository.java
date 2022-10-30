package com.example.crud.modules.auth.repositories;

import com.example.crud.modules.auth.entities.Pin;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PinRepository extends JpaRepository<Pin, Long> {

    Optional<Pin> findByPinEqualsAndTypeEquals(String pin, String type);
}