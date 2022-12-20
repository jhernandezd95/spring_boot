package com.example.crud.modules.product.dto;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
public class IncrementSaleQuantityDto implements Serializable {
	@NotNull
	private Long productId;
	@NotNull
	@Min(0)
	private int quantity;

	public IncrementSaleQuantityDto() {
	}

	public IncrementSaleQuantityDto(long productId, int quantity) {
		this.productId = productId;
		this.quantity = quantity;
	}
}
