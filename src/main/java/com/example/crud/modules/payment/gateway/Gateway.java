package com.example.crud.modules.payment.gateway;

import com.example.crud.modules.payment.entities.Payment;

public interface Gateway {

	void makePayment(Payment payment);

	void confirmPayment(Object confirmPaymentData);
}
