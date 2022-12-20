package com.example.crud.modules.product.dto;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.List;

/**
 * A DTO for the {@link com.example.crud.modules.product.entities.Product} entity
 */
@Data
public class CreateProductDto implements Serializable {
	@NotNull
	@NotBlank
	private final String name;
	private final String description;
	@Size(min = 1, max = 6)
	private final List<String> tags;
	@Min(0)
	private final double price;
	@Min(0)
	private final double cost;
	@NotNull
	private final Long categoryId;
	@NotNull
	private final Long storeId;
	@NotNull
	private final Long brandId;

	public CreateProductDto(String name, String description, List<String> tags, double price, double cost, Long categoryId, Long storeId, Long brandId) {
		this.name = name;
		this.description = description;
		this.tags = tags;
		this.price = price;
		this.cost = cost;
		this.categoryId = categoryId;
		this.storeId = storeId;
		this.brandId = brandId;
	}
}