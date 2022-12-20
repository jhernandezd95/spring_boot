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
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
@Table(name = "brand")
@EntityListeners(AuditingEntityListener.class)
@Where(clause = "deleted_at IS NULL")
@SQLDelete(sql = "UPDATE brand SET `brand`.`deleted_at` = CURRENT_TIMESTAMP WHERE id=?")
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

	@CreatedDate
	private Date createdAt;

	@LastModifiedDate
	private Date updatedAt;

	private Date deletedAt;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "created_by_id")
	@CreatedBy
	private User createdBy;

	public Brand() {
	}

	public Brand(String name, String description, User createdBy) {
		this.name = name;
		this.description = description;
		this.createdBy = createdBy;
	}

}