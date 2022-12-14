package com.example.crud.modules.product.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "product")
@EntityListeners(AuditingEntityListener.class)
@Where(clause = "deleted_at IS NULL")
@SQLDelete(sql = "UPDATE user SET `user`.`deleted_at` = CURRENT_TIMESTAMP WHERE id=?")
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

	@ManyToOne(targetEntity = Category.class, cascade = CascadeType.ALL)
	private Category category;

	@ManyToOne(targetEntity = Brand.class, cascade = CascadeType.ALL)
	private Brand brand;

	public Product() {
	}
}