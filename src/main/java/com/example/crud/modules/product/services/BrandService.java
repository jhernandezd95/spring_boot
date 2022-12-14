package com.example.crud.modules.product.services;

import com.example.crud.common.http_errors.NotFoundException;
import com.example.crud.modules.product.entities.Brand;
import com.example.crud.modules.product.entities.Product;
import com.example.crud.modules.product.repositories.BrandRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class BrandService {

	private BrandRepository brandRepository;

	public List<Brand> getAll() {
		return brandRepository.findAll();
	}

	public Brand getById(Long brandId) {
		Optional<Brand> brand = brandRepository.findById(brandId);

		if (brand.isEmpty()) {
			throw new NotFoundException("Brand not found with id " + brandId);
		}

		return brand.get();
	}

	public void remove(Long brandId) {
		Brand brand = this.getById(brandId);

		brandRepository.delete(brand);
	}
}
