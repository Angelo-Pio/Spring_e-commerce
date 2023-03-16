package com.eng.app.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

import org.hibernate.validator.constraints.UniqueElements;

import com.fasterxml.jackson.databind.ser.std.StdArraySerializers.FloatArraySerializer;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "carts")
public class Cart {


	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
//	@ManyToOne(optional = false )
//	@JoinColumn(name="cart_id")
	private Integer id;
	
	@Column(nullable = false)
	@NotNull
	private Integer product_id;
	
	@PositiveOrZero
	@NotNull
	private Integer product_quantity;
	
	@Column(unique = false, nullable = false, length = 255 )
	@NotNull
	private String product_name;
	
	@Column(nullable = false)
	@PositiveOrZero
	@NotNull
	private Float product_cost;
	
	
	
	
	
}
