package com.example.crud.modules.sale.entities;

import com.example.crud.modules.auth.entities.User;
import com.example.crud.modules.payment.entities.Payment;
import com.example.crud.modules.sale.enums.SaleStatus;
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
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "sale")
@EntityListeners(AuditingEntityListener.class)
@Where(clause = "deleted_at IS NULL")
@SQLDelete(sql = "UPDATE sale SET `sale`.`deleted_at` = CURRENT_TIMESTAMP WHERE id=?")
@Getter
@Setter
@AllArgsConstructor
public class Sale {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id", nullable = false)
	private Long id;

	@Column(nullable = true, unique = true)
	private String code;

	@NotNull
	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private SaleStatus status;

	@CreatedDate
	private Date createdAt;

	@LastModifiedDate
	private Date updatedAt;

	private Date deletedAt;

	@OneToMany(mappedBy = "sale", cascade = CascadeType.ALL)
	private Set<SaleProduct> saleProducts;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "created_by_id")
	@CreatedBy
	private User createdBy;

	@OneToMany(mappedBy = "sale", cascade = CascadeType.ALL)
	private Set<Payment> payments;

	public Sale() {
	}

	public Sale(SaleStatus status, User user) {
		this.status = status;
		this.createdBy = user;
	}

	public void setCode() {
		this.code = "SALE" + this.id;
	}
}