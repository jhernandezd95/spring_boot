package com.example.crud.modules.sale.repositories;

import com.example.crud.modules.sale.entities.SaleProduct;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.Optional;

public interface SaleProductRepository extends JpaRepository<SaleProduct, Long> {

	public Optional<Collection<SaleProduct>> findBySale_Id(Long saleId);
}