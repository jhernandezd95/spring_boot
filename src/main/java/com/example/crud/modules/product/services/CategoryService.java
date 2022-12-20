package com.example.crud.modules.product.services;

import com.example.crud.common.http_errors.NotFoundException;
import com.example.crud.modules.auth.entities.User;
import com.example.crud.modules.auth.services.AuthService;
import com.example.crud.modules.product.dto.CreateCategoryDto;
import com.example.crud.modules.product.entities.Category;
import com.example.crud.modules.product.repositories.CategoryRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class CategoryService {

	private CategoryRepository categoryRepository;
	private AuthService authService;

	public List<Category> getAll() {
		return categoryRepository.findAll();
	}

	public Category getById(Long categoryId) {
		Optional<Category> category = categoryRepository.findById(categoryId);

		if (category.isEmpty()) {
			throw new NotFoundException("Category not found with id " + categoryId);
		}

		return category.get();
	}

	public void remove(Long categoryId) {
		Category category = this.getById(categoryId);

		categoryRepository.delete(category);
	}

	public Category createCategory(CreateCategoryDto createCategoryDto) {
		User createdBy = this.authService.getLoggedUser();

		Category category = new Category(createCategoryDto.getName(), createCategoryDto.getDescription(), createdBy);

		categoryRepository.save(category);

		return category;
	}
}
