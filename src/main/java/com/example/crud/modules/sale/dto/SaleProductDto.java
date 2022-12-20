package com.example.crud.modules.sale.dto;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * A DTO for the {@link com.example.crud.modules.sale.entities.SaleProduct} entity
 */
@Data
public class SaleProductDto implements Serializable {
	@NotNull
	@Min(0)
	private final int productAmount;
	@NotNull
	private final Long productId;
}