package com.example.crud.modules.payment.repositories;

import com.example.crud.modules.payment.entities.Payment;
import com.example.crud.modules.payment.enums.PaymentStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.Optional;

public interface PaymentRepository extends JpaRepository<Payment, Long> {

	Optional<Collection<Payment>> findBySale_Id(Long saleId);

	Optional<Payment> findFirstBySale_IdAndStatusEquals(Long saleId, PaymentStatus status);
}