package com.example.crud.modules.product.controller;

import com.example.crud.modules.auth.entities.User;
import com.example.crud.modules.product.entities.Product;
import com.example.crud.modules.product.services.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import java.util.List;

@Controller
@RequestMapping(path = "/v1")
@AllArgsConstructor
public class ProductController {

	private ProductService productService;

	private static final String PATH = "/product";
	private static final String PATH_ONE = PATH + "/{productId}";
	private static final String PATH_ONE_BY_SLUG = PATH + "/slug/{slug}";

	@GetMapping(path = PATH)
	public @ResponseBody Iterable<Product> getAllProducts() {
		List<Product> products = productService.getAll();
		return products;
	}

	@GetMapping(path = PATH_ONE)
	public @ResponseBody Product getProductById(@PathVariable @NotNull @DecimalMin("0") Long productId) {
		return productService.getById(productId);
	}

	@GetMapping(path = PATH_ONE_BY_SLUG)
	public @ResponseBody Product getProductById(@PathVariable @NotNull String slug) {
		return productService.getBySlug(slug);
	}

	@DeleteMapping(path = PATH_ONE)
	@PreAuthorize("hasAuthority('owner')")
	public ResponseEntity removeUser(@PathVariable @NotNull @DecimalMin("0") Long productId) {
		productService.remove(productId);
		return ResponseEntity.ok().build();
	}


}
