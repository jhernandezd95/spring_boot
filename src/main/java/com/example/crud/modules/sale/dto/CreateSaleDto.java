package com.example.crud.modules.sale.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Collection;

/**
 * A DTO for the {@link com.example.crud.modules.sale.entities.Sale} entity
 */
@Data
public class CreateSaleDto implements Serializable {
	@NotNull
	private final Collection<SaleProductDto> products;
	@NotNull
	private final Long userId;
}