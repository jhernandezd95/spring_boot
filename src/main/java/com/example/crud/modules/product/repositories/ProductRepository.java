package com.example.crud.modules.product.repositories;

import com.example.crud.modules.product.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {

	Optional<Product> findFirstBySlugEquals(String slug);
}