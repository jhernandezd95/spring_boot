package com.example.crud.modules.product.services;

import com.example.crud.common.http_errors.NotFoundException;
import com.example.crud.modules.auth.entities.User;
import com.example.crud.modules.product.entities.Product;
import com.example.crud.modules.product.repositories.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ProductService {

	private ProductRepository productRepository;

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
