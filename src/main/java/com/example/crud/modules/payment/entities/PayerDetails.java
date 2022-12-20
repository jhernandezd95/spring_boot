package com.example.crud.modules.payment.entities;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class PayerDetails {

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
	@NotNull
	@NotBlank
	private String phoneNumber;

	public PayerDetails(String name, String lastName, String email, String phoneNumber) {
		this.name = name;
		this.lastName = lastName;
		this.email = email;
		this.phoneNumber = phoneNumber;
	}
}
