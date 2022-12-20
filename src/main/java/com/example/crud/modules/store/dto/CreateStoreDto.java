package com.example.crud.modules.store.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * A DTO for the {@link com.example.crud.modules.store.entities.Store} entity
 */
@Data
public class CreateStoreDto implements Serializable {
	@NotNull
	@NotBlank
	private final String name;
	@NotNull
	@NotBlank
	private final String description;
	@NotNull
	private final Long userId;
}