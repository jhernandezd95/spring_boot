package com.example.crud.modules.payment.dto;

import com.example.crud.modules.payment.enums.Gateway;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * A DTO for the {@link com.example.crud.modules.payment.entities.Payment} entity
 */
@Data
public class CreatePaymentDto implements Serializable {
	private final String note;
	@NotEmpty
	@NotBlank
	private final String transaction;
	@NotNull
	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private final Gateway gateway;
	@NotNull
	private final Long saleId;
	@NotNull
	private final PayerDetailsDto payerDetails;
}