package com.example.crud.modules.product.repositories;

import com.example.crud.modules.product.entities.Brand;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BrandRepository extends JpaRepository<Brand, Long> {
}