package com.example.crud.modules.sale.entities;

import com.example.crud.modules.product.entities.Product;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Where(clause = "deleted_at IS NULL")
@SQLDelete(sql = "UPDATE sale_product SET `sale_product`.`deleted_at` = CURRENT_TIMESTAMP WHERE id=?")
@Getter
@Setter
@AllArgsConstructor
public class SaleProduct implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id", nullable = false)
	private Long id;

	@NotNull
	@Min(0)
	@Column(nullable = false)
	private double priceAtSale;

	@NotNull
	@Min(0)
	@Column(nullable = false)
	private int productAmount;

	@CreatedDate
	@Column(nullable = false)
	private Date createdAt;

	@LastModifiedDate
	@Column(nullable = false)
	private Date updatedAt;

	@Column()
	private Date deletedAt;

	@NotNull
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "product_id", nullable = false)
	private Product product;

	@NotNull
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "sale_id", nullable = false)
	private Sale sale;

	public SaleProduct() {
	}

	public SaleProduct(Double price, int amount, Product product, Sale sale) {
		this.priceAtSale = price;
		this.productAmount = amount;
		this.product = product;
		this.sale = sale;
	}

}