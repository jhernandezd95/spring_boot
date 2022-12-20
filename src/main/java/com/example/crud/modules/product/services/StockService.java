package com.example.crud.modules.product.services;

import com.example.crud.common.http_errors.BadRequestException;
import com.example.crud.common.http_errors.NotFoundException;
import com.example.crud.modules.auth.entities.User;
import com.example.crud.modules.auth.services.AuthService;
import com.example.crud.modules.product.dto.CreateStockDto;
import com.example.crud.modules.product.dto.IncrementSaleQuantityDto;
import com.example.crud.modules.product.entities.Product;
import com.example.crud.modules.product.entities.Stock;
import com.example.crud.modules.product.repositories.StockRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;

@Service
@AllArgsConstructor
public class StockService {

	private StockRepository stockRepository;
	private AuthService authService;
	private ProductService productService;

	public Stock createStock(CreateStockDto createStockDto) {
		User createdBy = this.authService.getLoggedUser();
		Product product = this.productService.getById(createStockDto.getProductId());

		Stock stock = new Stock(product, createStockDto.getQuantity(), createdBy);

		this.stockRepository.save(stock);

		return stock;
	}

	public Collection<Stock> getAllByProductId(Long productId) {
		return this.stockRepository.findByProduct_Id(productId);
	}

	public Stock getById(Long stockId) {
		Optional<Stock> optionalStock = this.stockRepository.findById(stockId);

		if (optionalStock.isEmpty()) {
			throw new NotFoundException("Stock not found with id " + stockId);
		}

		return optionalStock.get();
	}

	public void incrementSaleQuantity(IncrementSaleQuantityDto incrementSaleQuantityDto) {
		Optional<Collection<Stock>> optionalStocks = this.stockRepository.findAllStockAvailabilityGivenAProduct_Id(incrementSaleQuantityDto.getProductId());

		if (optionalStocks.isEmpty()) {
			throw new BadRequestException("There is no stock for product with id " + incrementSaleQuantityDto.getProductId());
		}

		Collection<Stock> stocks = optionalStocks.get();

		int actualQuantity = stocks.stream().mapToInt(stock -> stock.getQuantity() - stock.getSaleQuantity()).sum();

		if (actualQuantity < incrementSaleQuantityDto.getQuantity()) {
			throw new BadRequestException("There is no availability in stock for " + incrementSaleQuantityDto.getQuantity() + " quantity");
		}

		for (Stock stock : stocks) {
			if (stock.getAvailability() >= incrementSaleQuantityDto.getQuantity()) {
				stock.incrementSale(incrementSaleQuantityDto.getQuantity());
				this.stockRepository.save(stock);
				break;
			} else {
				stock.setSaleQuantity(stock.getQuantity());
				this.stockRepository.save(stock);
			}
		}

	}
}
