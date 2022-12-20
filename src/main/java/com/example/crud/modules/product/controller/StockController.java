package com.example.crud.modules.product.controller;

import com.example.crud.modules.product.dto.CreateStockDto;
import com.example.crud.modules.product.entities.Stock;
import com.example.crud.modules.product.services.StockService;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;

@Controller
@RequestMapping(path = "/v1")
@AllArgsConstructor
public class StockController {

	private static final String PATH = "/stock";
	private static final String PATH_ONE = PATH + "/{stockId}";
	private static final String PATH_ALL_STOCKS_BY_PRODUCT_ID = PATH + "/stock/product/{productId}";
	private StockService stockService;

	@PostMapping(path = PATH)
	@PreAuthorize("hasAuthority('operator')")
	public @ResponseBody Stock addStock(@RequestBody CreateStockDto createStockDto) {
		return stockService.createStock(createStockDto);
	}

	@GetMapping(path = PATH_ALL_STOCKS_BY_PRODUCT_ID)
	@PreAuthorize("hasAuthority('operator')")
	public @ResponseBody Iterable<Stock> getAllStockByProductId(@PathVariable @NotNull @DecimalMin("0") Long productId) {
		return stockService.getAllByProductId(productId);
	}

	@GetMapping(path = PATH_ONE)
	@PreAuthorize("hasAuthority('operator')")
	public @ResponseBody Stock getStockById(@PathVariable @NotNull @DecimalMin("0") Long stockId) {
		return stockService.getById(stockId);
	}
}
