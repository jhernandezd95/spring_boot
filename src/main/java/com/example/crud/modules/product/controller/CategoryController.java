package com.example.crud.modules.product.controller;

import com.example.crud.modules.product.dto.CreateCategoryDto;
import com.example.crud.modules.product.entities.Category;
import com.example.crud.modules.product.services.CategoryService;
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
public class CategoryController {

	private CategoryService categoryService;

	private static final String PATH = "/category";
	private static final String PATH_ONE = PATH + "/{categoryId}";

	@PostMapping(path = PATH)
	@PreAuthorize("hasAuthority('admin')")
	public @ResponseBody Category createCategory(@Validated @RequestBody CreateCategoryDto createCategoryDto) {
		return categoryService.createCategory(createCategoryDto);
	}

	@GetMapping(path = PATH)
	@PreAuthorize("hasAuthority('operator')")
	public @ResponseBody Iterable<Category> getAllCategories() {
		return categoryService.getAll();
	}

	@GetMapping(path = PATH_ONE)
	@PreAuthorize("hasAuthority('operator')")
	public @ResponseBody Category getCategoryById(@PathVariable @NotNull @DecimalMin("0") Long categoryId) {
		return categoryService.getById(categoryId);
	}

	@DeleteMapping(path = PATH_ONE)
	@PreAuthorize("hasAuthority('admin')")
	public ResponseEntity removeCategory(@PathVariable @NotNull @DecimalMin("0") Long categoryId) {
		categoryService.remove(categoryId);
		return ResponseEntity.ok().build();
	}
}
