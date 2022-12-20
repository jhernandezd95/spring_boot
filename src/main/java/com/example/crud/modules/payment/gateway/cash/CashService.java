package com.example.crud.modules.payment.gateway.cash;

import com.example.crud.modules.mail.dto.EmailDto;
import com.example.crud.modules.mail.service.EmailService;
import com.example.crud.modules.payment.entities.Payment;
import com.example.crud.modules.payment.enums.PaymentStatus;
import com.example.crud.modules.payment.gateway.Gateway;
import com.example.crud.modules.payment.repositories.PaymentRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@AllArgsConstructor
public class CashService implements Gateway {

	private PaymentRepository paymentRepository;
	private EmailService emailService;

	@Override
	public void makePayment(Payment payment) {
	}

	@Override
	public void confirmPayment(Object confirmPaymentData) {
		Payment payment = (Payment) confirmPaymentData;

		payment.setStatus(PaymentStatus.PAYED);
		payment.setPayedAt(new Date(System.currentTimeMillis()));

		this.paymentRepository.save(payment);

		String code = payment.getSale().getCode();
		String body = "Estimado cliente, se ha confirmado el pago de su pedido con codigo " + code + ".";
		EmailDto emailDto = new EmailDto(payment.getPayerDetails().getEmail(), body, "Pago exitoso");
		emailService.sendSimpleMail(emailDto);
	}

}
