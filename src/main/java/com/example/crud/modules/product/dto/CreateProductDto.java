package com.example.crud.modules.product.dto;

import com.example.crud.modules.product.entities.Brand;
import com.example.crud.modules.product.entities.Category;
import com.example.crud.modules.product.entities.Product;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.text.Normalizer;
import java.util.List;
import java.util.Locale;
import java.util.regex.Pattern;

/**
 * A DTO for the {@link com.example.crud.modules.product.entities.Product} entity
 */
@Data
public class CreateProductDto implements Serializable {
	@NotNull
	@NotBlank
	private final String name;

	@NotNull
	@NotBlank
	private String slug;

	private String description;

	@Size(min = 1, max = 6)
	private List<String> tags;

	@Min(0)
	private double price;

	@Min(0)
	private double cost;

	private Brand brand;

	private Category category;

	public CreateProductDto(String name, String description, List<String> tags, double price, double cost, Category category, Brand brand) {
		this.name = name;
		this.description = description;
		this.tags = tags;
		this.price = price;
		this.cost = cost;
		this.slug = toSlug(name);
		this.category = category;
		this.brand = brand;
	}

	private static String toSlug(String input) {

		Pattern NONLATIN = Pattern.compile("[^\\w-]");
		Pattern WHITESPACE = Pattern.compile("[\\s]");

		String nowhitespace = WHITESPACE.matcher(input).replaceAll("-");
		String normalized = Normalizer.normalize(nowhitespace, Normalizer.Form.NFD);
		String slug = NONLATIN.matcher(normalized).replaceAll("");
		return slug.toLowerCase(Locale.ENGLISH);
	}
}