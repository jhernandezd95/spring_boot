package com.example.crud.modules.payment.controller;

import com.example.crud.modules.payment.dto.CreatePaymentDto;
import com.example.crud.modules.payment.entities.Payment;
import com.example.crud.modules.payment.enums.PaymentStatus;
import com.example.crud.modules.payment.services.PaymentServices;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;

@Controller
@RequestMapping(path = "/v1")
@AllArgsConstructor
public class PaymentController {

	private static final String PATH = "/payment";
	private static final String PATH_ONE = PATH + "/{paymentId}";
	private static final String PATH_ONE_BY_SALE = PATH + "-sale/{saleId}";
	private PaymentServices paymentServices;

	@PostMapping(path = PATH)
	@PreAuthorize("hasAuthority('client')")
	public @ResponseBody Payment createPayment(@Validated @RequestBody CreatePaymentDto paymentDto) {
		return paymentServices.createPayment(paymentDto);
	}

	@GetMapping(path = PATH_ONE)
	@PreAuthorize("hasAuthority('client')")
	public @ResponseBody Payment getOne(@PathVariable @NotNull @DecimalMin("0") Long paymentId) {
		return paymentServices.getOnePayment(paymentId);
	}

	@GetMapping(path = PATH_ONE_BY_SALE)
	@PreAuthorize("hasAuthority('client')")
	public @ResponseBody Payment getOneBySaleId(@PathVariable @NotNull @DecimalMin("0") Long saleId) {
		return paymentServices.getOnePaymentBySaleId(saleId, PaymentStatus.PAYED);
	}

}
