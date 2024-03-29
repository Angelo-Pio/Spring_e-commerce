package com.eng.app.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import javax.validation.constraints.PositiveOrZero;

import org.hibernate.validator.constraints.Length;
import org.springframework.lang.Nullable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.bytebuddy.utility.nullability.MaybeNull;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "carts")
public class Cart {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@NotNull
	@PositiveOrZero
	@Column(nullable = false)
	private Integer product_id;
	
	@PositiveOrZero
	@NotNull
	private Integer user_id;
	
	@PositiveOrZero
	@NotNull
	private Integer product_quantity;
	
	@NotNull
	@Length(max = 255)
	private String product_name;
	
	@Column(nullable = false)
	@PositiveOrZero
	@NotNull
	private Float product_cost;
	
	
}
