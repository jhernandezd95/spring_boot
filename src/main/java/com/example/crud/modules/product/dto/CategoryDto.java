package com.example.crud.modules.product.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * A DTO for the {@link com.example.crud.modules.product.entities.Category} entity
 */
@Data
public class CategoryDto implements Serializable {
	@NotNull
	@NotBlank
	private final String name;
	@NotNull
	@NotBlank
	private final String description;
}