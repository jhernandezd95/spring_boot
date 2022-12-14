package com.example.crud.modules.product.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "brand")
@EntityListeners(AuditingEntityListener.class)
@Where(clause = "deleted_at IS NULL")
@SQLDelete(sql = "UPDATE user SET `user`.`deleted_at` = CURRENT_TIMESTAMP WHERE id=?")
@Getter
@Setter
@AllArgsConstructor
public class Brand {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id", nullable = false)
	private Long id;

	@NotNull
	@NotBlank
	@Column(nullable = false, unique = true)
	private String name;

	@NotNull
	@NotBlank
	@Column(nullable = false)
	private String description;

	public Brand() {
	}

}