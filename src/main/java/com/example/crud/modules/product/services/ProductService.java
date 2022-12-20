package com.example.crud.modules.product.services;

import com.example.crud.common.http_errors.NotFoundException;
import com.example.crud.modules.auth.entities.User;
import com.example.crud.modules.auth.services.AuthService;
import com.example.crud.modules.product.dto.CreateProductDto;
import com.example.crud.modules.product.entities.Brand;
import com.example.crud.modules.product.entities.Category;
import com.example.crud.modules.product.entities.Product;
import com.example.crud.modules.product.repositories.ProductRepository;
import com.example.crud.modules.store.entities.Store;
import com.example.crud.modules.store.services.StoreService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ProductService {

	private ProductRepository productRepository;
	private AuthService authService;
	private CategoryService categoryService;
	private StoreService storeService;
	private BrandService brandService;

	public Product createProduct(CreateProductDto createProductDto) {
		User createdBy = this.authService.getLoggedUser();

		Category category = categoryService.getById(createProductDto.getCategoryId());
		Brand brand = brandService.getById(createProductDto.getBrandId());
		Store store = storeService.getById(createProductDto.getStoreId());

		Product product = new Product(
				createProductDto.getName(),
				createProductDto.getDescription(),
				createProductDto.getTags(),
				createProductDto.getPrice(),
				createProductDto.getCost(),
				category,
				store,
				brand,
				createdBy
		);

		productRepository.save(product);

		return product;
	}

	public List<Product> getAll() {
		return productRepository.findAll();
	}


	public Product getById(Long productId) {
		Optional<Product> product = productRepository.findById(productId);

		if (product.isEmpty()) {
			throw new NotFoundException("Product not found with id " + productId);
		}

		return product.get();
	}

	public Product getBySlug(String slug) {
		Optional<Product> product = productRepository.findFirstBySlugEquals(slug);

		if (product.isEmpty()) {
			throw new NotFoundException("Product not found with slug " + slug);
		}

		return product.get();
	}

	public void remove(Long productId) {
		Product product = this.getById(productId);

		productRepository.delete(product);
	}
}
