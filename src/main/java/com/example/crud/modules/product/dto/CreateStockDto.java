package com.example.crud.modules.product.dto;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * A DTO for the {@link com.example.crud.modules.product.entities.Stock} entity
 */
@Data
public class CreateStockDto implements Serializable {
	@NotNull
	private final Long productId;
	@NotNull
	@Min(0)
	private final int quantity;
}