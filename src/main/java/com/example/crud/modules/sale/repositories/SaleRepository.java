package com.example.crud.modules.sale.repositories;

import com.example.crud.modules.sale.entities.Sale;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SaleRepository extends JpaRepository<Sale, Long> {
}