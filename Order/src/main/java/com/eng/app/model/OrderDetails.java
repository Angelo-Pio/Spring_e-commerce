package com.eng.app.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

import org.hibernate.validator.constraints.Length;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="order_details")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class OrderDetails {
	
	@Id
	private Integer order_id;
	
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
