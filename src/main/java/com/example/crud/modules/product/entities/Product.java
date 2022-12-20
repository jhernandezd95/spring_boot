package com.example.crud.modules.product.entities;

import com.example.crud.modules.auth.entities.User;
import com.example.crud.modules.sale.entities.SaleProduct;
import com.example.crud.modules.store.entities.Store;
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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.text.Normalizer;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.regex.Pattern;

@Entity
@Table(name = "product")
@EntityListeners(AuditingEntityListener.class)
@Where(clause = "deleted_at IS NULL")
@SQLDelete(sql = "UPDATE product SET `product`.`deleted_at` = CURRENT_TIMESTAMP WHERE id=?")
@Getter
@Setter
@AllArgsConstructor
public class Product {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id", nullable = false)
	private Long id;

	@NotNull
	@NotBlank
	@Column(nullable = false)
	private String name;

	@NotNull
	@NotBlank
	@Column(unique = true, nullable = false)
	private String slug;

	@Column()
	private String description;

	@Size(min = 1, max = 6)
	@Column(nullable = false)
	@ElementCollection
	private List<@NotNull @NotBlank String> tags;

	@Min(0)
	@Column(nullable = false)
	private double price;

	@Min(0)
	@Column(nullable = false)
	private double cost = 0;

	@CreatedDate
	private Date createdAt;

	@LastModifiedDate
	private Date updatedAt;

	private Date deletedAt;

	@OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
	private Set<SaleProduct> saleProducts;

	@NotNull
	@ManyToOne(targetEntity = Category.class, cascade = CascadeType.ALL)
	private Category category;

	@NotNull
	@ManyToOne(targetEntity = Store.class, cascade = CascadeType.ALL)
	private Store store;

	@NotNull
	@ManyToOne(targetEntity = Brand.class, cascade = CascadeType.ALL)
	private Brand brand;

	@NotNull
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "created_by_id")
	@CreatedBy
	private User createdBy;

	public Product() {
	}

	public Product(String name, String description, List<String> tags, double price, double cost, Category category, Store store, Brand brand, User createdBy) {
		this.name = name;
		this.description = description;
		this.tags = tags;
		this.price = price;
		this.cost = cost;
		this.slug = this.toSlug(name);
		this.category = category;
		this.store = store;
		this.brand = brand;
		this.createdBy = createdBy;
	}

	private String toSlug(String input) {

		java.util.regex.Pattern NONLATIN = java.util.regex.Pattern.compile("[^\\w-]");
		java.util.regex.Pattern WHITESPACE = Pattern.compile("[\\s]");

		String nowhitespace = WHITESPACE.matcher(input).replaceAll("-");
		String normalized = Normalizer.normalize(nowhitespace, Normalizer.Form.NFD);
		String slug = NONLATIN.matcher(normalized).replaceAll("");
		return slug.toLowerCase(Locale.ENGLISH);
	}
}