package com.example.crud.modules.product.controller;

import com.example.crud.modules.product.entities.Category;
import com.example.crud.modules.product.services.CategoryService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;

@Controller
@RequestMapping(path = "/v1")
@AllArgsConstructor
public class CategoryController {

	private CategoryService categoryService;

	private static final String PATH = "/category";
	private static final String PATH_ONE = PATH + "/{categoryId}";

	@GetMapping(path = PATH)
	public @ResponseBody Iterable<Category> getAllCategories() {
		return categoryService.getAll();
	}

	@GetMapping(path = PATH_ONE)
	public @ResponseBody Category getCategoryById(@PathVariable @NotNull @DecimalMin("0") Long categoryId) {
		return categoryService.getById(categoryId);
	}

	@DeleteMapping(path = PATH_ONE)
	@PreAuthorize("hasAuthority('owner')")
	public ResponseEntity removeCategory(@PathVariable @NotNull @DecimalMin("0") Long categoryId) {
		categoryService.remove(categoryId);
		return ResponseEntity.ok().build();
	}
}
