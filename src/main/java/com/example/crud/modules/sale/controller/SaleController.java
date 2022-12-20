package com.example.crud.modules.sale.controller;

import com.example.crud.modules.sale.dto.CreateSaleDto;
import com.example.crud.modules.sale.dto.ResCreateSaleDto;
import com.example.crud.modules.sale.entities.Sale;
import com.example.crud.modules.sale.services.SaleServices;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;

@Controller
@RequestMapping(path = "/v1")
@AllArgsConstructor
public class SaleController {

	private static final String PATH = "/sale";
	private static final String PATH_ONE = PATH + "/{saleId}";
	private SaleServices saleServices;

	@PostMapping(path = PATH)
	@PreAuthorize("hasAuthority('client')")
	public @ResponseBody ResCreateSaleDto createSale(@Validated @RequestBody CreateSaleDto createSaleDto) {
		return saleServices.createSale(createSaleDto);
	}

	@GetMapping(path = PATH_ONE)
	@PreAuthorize("hasAuthority('client')")
	public @ResponseBody Sale createSale(@PathVariable @NotNull @DecimalMin("0") Long saleId) {
		return saleServices.getOne(saleId);
	}
}
