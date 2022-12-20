package com.example.crud.modules.payment.dto;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
public class PayerDetailsDto implements Serializable {
	@NotNull
	@NotBlank
	private String name;
	@NotNull
	@NotBlank
	private String lastName;
	@Email
	@NotNull
	@NotBlank
	private String email;
	@NotBlank
	private String phoneNumber;
}
