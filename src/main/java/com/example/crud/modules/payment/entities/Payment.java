package com.example.crud.modules.payment.entities;

import com.example.crud.modules.auth.entities.User;
import com.example.crud.modules.payment.dto.PayerDetailsDto;
import com.example.crud.modules.payment.enums.Gateway;
import com.example.crud.modules.payment.enums.PaymentStatus;
import com.example.crud.modules.sale.entities.Sale;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
@Table(name = "payment")
@EntityListeners(AuditingEntityListener.class)
@Where(clause = "deleted_at IS NULL")
@SQLDelete(sql = "UPDATE payment SET `payment`.`deleted_at` = CURRENT_TIMESTAMP WHERE id=?")
@Getter
@Setter
@AllArgsConstructor
public class Payment {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id", nullable = false)
	private Long id;

	private String note;

	@NotEmpty
	@NotBlank
	@Column(nullable = false)
	private String transaction;

	@Min(0)
	@NotNull
	@Column(nullable = false)
	private double amount;

	@Column()
	private Date payedAt;

	@NotNull
	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private PaymentStatus status;

	@NotNull
	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private Gateway gateway;

	@Column()
	private Date responseAt;

	@CreatedDate
	private Date createdAt;

	@LastModifiedDate
	private Date updatedAt;

	private Date deletedAt;

	@Embedded
	private PayerDetails payerDetails;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "sale_id", nullable = false)
	private Sale sale;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "created_by_id")
	@CreatedBy
	private User createdBy;

	public Payment() {
	}

	public Payment(String note, String transaction, Double amount, Gateway gateway, PayerDetailsDto payerDetails, PaymentStatus status) {
		this.note = note;
		this.transaction = transaction;
		this.amount = amount;
		this.gateway = gateway;
		this.payerDetails = new PayerDetails(payerDetails.getName(), payerDetails.getLastName(), payerDetails.getEmail(), payerDetails.getPhoneNumber());
		this.status = status;
	}

	public Payment(String note, Double amount, Gateway gateway, PayerDetailsDto payerDetails, PaymentStatus status) {
		this.note = note;
		this.amount = amount;
		this.gateway = gateway;
		this.payerDetails = new PayerDetails(payerDetails.getName(), payerDetails.getLastName(), payerDetails.getEmail(), payerDetails.getPhoneNumber());
		this.status = status;
	}

}