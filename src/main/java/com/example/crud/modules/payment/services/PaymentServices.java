package com.example.crud.modules.payment.services;

import com.example.crud.common.http_errors.BadRequestException;
import com.example.crud.common.http_errors.NotFoundException;
import com.example.crud.modules.payment.dto.CreatePaymentDto;
import com.example.crud.modules.payment.entities.Payment;
import com.example.crud.modules.payment.enums.Gateway;
import com.example.crud.modules.payment.enums.PaymentStatus;
import com.example.crud.modules.payment.gateway.cash.CashService;
import com.example.crud.modules.payment.repositories.PaymentRepository;
import com.example.crud.modules.sale.services.SaleServices;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Optional;

@Service
@AllArgsConstructor
public class PaymentServices {

	private PaymentRepository paymentRepository;
	private SaleServices saleServices;
	private CashService cashService;

	@Transactional
	public Payment createPayment(CreatePaymentDto createPaymentDto) {
		double amount = this.saleServices.getAmount(createPaymentDto.getSaleId());

		Payment payment = new Payment(createPaymentDto.getNote(), createPaymentDto.getTransaction(), amount, Gateway.CASH, createPaymentDto.getPayerDetails(), PaymentStatus.REQUESTED);

		paymentRepository.save(payment);

		switch (createPaymentDto.getGateway()) {
			case CASH: {
				this.cashService.confirmPayment(payment);
				break;
			}
			default: {
				throw new BadRequestException("Gateway " + createPaymentDto.getGateway() + " not found.");
			}
		}

		return this.getOnePayment(payment.getId());
	}

	public Payment getOnePayment(Long paymentId) {
		Optional<Payment> optionalPayment = paymentRepository.findById(paymentId);

		if (optionalPayment.isEmpty()) {
			throw new NotFoundException("Payment not found with id " + paymentId);
		}

		return optionalPayment.get();
	}

	public Collection<Payment> getAllPaymentBySaleId(Long saleId) {
		Optional<Collection<Payment>> optionalPayments = paymentRepository.findBySale_Id(saleId);

		if (optionalPayments.isEmpty()) {
			throw new NotFoundException("There are not Payment related to a sale with id " + saleId);
		}

		return optionalPayments.get();
	}

	public Payment getOnePaymentBySaleId(Long saleId, PaymentStatus status) {
		Optional<Payment> optionalPayment = paymentRepository.findFirstBySale_IdAndStatusEquals(saleId, status);

		if (optionalPayment.isEmpty()) {
			throw new NotFoundException("There are not Payment related to a sale with id " + saleId);
		}

		return optionalPayment.get();
	}
}
