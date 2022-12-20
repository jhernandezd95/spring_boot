package com.example.crud.modules.product.entities;

import com.example.crud.modules.auth.entities.User;
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
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
@Table(name = "stock")
@EntityListeners(AuditingEntityListener.class)
@Where(clause = "deleted_at IS NULL")
@SQLDelete(sql = "UPDATE stock SET `stock`.`deleted_at` = CURRENT_TIMESTAMP WHERE id=?")
@Getter
@Setter
@AllArgsConstructor
public class Stock {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id", nullable = false)
	private Long id;

	@NotNull
	@ManyToOne(targetEntity = Product.class, cascade = CascadeType.ALL)
	private Product product;

	@NotNull
	@Min(0)
	private int quantity;

	private int saleQuantity;

	@CreatedDate
	private Date createdAt;

	@LastModifiedDate
	private Date updatedAt;

	private Date deletedAt;

	@NotNull
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "created_by_id")
	@CreatedBy
	private User createdBy;

	public Stock() {
	}

	public Stock(Product product, int quantity, User createdBy) {
		this.product = product;
		this.saleQuantity = 0;
		this.createdBy = createdBy;
		this.quantity = quantity;
	}

	public int getAvailability() {
		return this.quantity - this.saleQuantity;
	}

	public void incrementSale(int value) {
		this.saleQuantity += value;
	}

	public void decrementSale(int value) {
		this.saleQuantity -= value;
	}
}