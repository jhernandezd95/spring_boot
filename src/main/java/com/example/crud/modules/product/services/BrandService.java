package com.example.crud.modules.product.services;

import com.example.crud.common.http_errors.NotFoundException;
import com.example.crud.modules.auth.entities.User;
import com.example.crud.modules.auth.services.AuthService;
import com.example.crud.modules.product.dto.CreateBrandDto;
import com.example.crud.modules.product.entities.Brand;
import com.example.crud.modules.product.repositories.BrandRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class BrandService {

	private BrandRepository brandRepository;
	private AuthService authService;

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

	public Brand createBrand(CreateBrandDto createBrand) {
		User createdBy = this.authService.getLoggedUser();

		Brand brand = new Brand(createBrand.getName(), createBrand.getDescription(), createdBy);

		brandRepository.save(brand);

		return brand;
	}
}
