package com.example.crud.modules.product.repositories;

import com.example.crud.modules.product.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}