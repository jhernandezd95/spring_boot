package com.example.crud.modules.product.controller;

import com.example.crud.modules.product.dto.CreateBrandDto;
import com.example.crud.modules.product.entities.Brand;
import com.example.crud.modules.product.services.BrandService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;

@Controller
@RequestMapping(path = "/v1")
@AllArgsConstructor
public class BrandController {

	private BrandService brandService;

	private static final String PATH = "/brand";
	private static final String PATH_ONE = PATH + "/{brandId}";

	@PostMapping(path = PATH)
	@PreAuthorize("hasAuthority('admin')")
	public @ResponseBody Brand createCategory(@Validated @RequestBody CreateBrandDto createBrandDto) {
		return brandService.createBrand(createBrandDto);
	}

	@GetMapping(path = PATH)
	@PreAuthorize("hasAuthority('operator')")
	public @ResponseBody Iterable<Brand> getAllCategories() {
		return brandService.getAll();
	}

	@GetMapping(path = PATH_ONE)
	@PreAuthorize("hasAuthority('operator')")
	public @ResponseBody Brand getBrandById(@PathVariable @NotNull @DecimalMin("0") Long brandId) {
		return brandService.getById(brandId);
	}

	@DeleteMapping(path = PATH_ONE)
	@PreAuthorize("hasAuthority('admin')")
	public ResponseEntity removeBrand(@PathVariable @NotNull @DecimalMin("0") Long brandId) {
		brandService.remove(brandId);
		return ResponseEntity.ok().build();
	}
}
