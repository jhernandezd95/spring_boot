package com.example.crud.modules.product.repositories;

import com.example.crud.modules.product.entities.Stock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Collection;
import java.util.Optional;

public interface StockRepository extends JpaRepository<Stock, Long> {

	Collection<Stock> findByProduct_Id(Long productId);

	@Query("SELECT stock FROM Stock AS stock WHERE  (stock.quantity - stock.saleQuantity) > 0 AND stock.product.id = :productId ORDER BY stock.createdAt")
	Optional<Collection<Stock>> findAllStockAvailabilityGivenAProduct_Id(@Param("productId") Long productId);
}