package com.example.crud.modules.store.repositories;

import com.example.crud.modules.store.entities.Store;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StoreRepository extends JpaRepository<Store, Long> {
}