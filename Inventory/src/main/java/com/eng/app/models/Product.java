package com.eng.app.models;


import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Check;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "products")
@Check(constraints =  "quantity >= 0 and cost > 0" )
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Product {
	
	public static final int MAX_DESCRIPTION_LEN = 500;

	public static final int MAX_NAME_LEN = 255;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer product_id;
	
	@Column(unique = true, nullable = false, columnDefinition = "varchar(255)" )
	private String name;
	
	@Column(unique = false, nullable = false, columnDefinition = "varchar("+ MAX_DESCRIPTION_LEN +")" )
	private String description;

	@Column(
			nullable = false,
			unique = false)
	private Integer quantity;
	
	@Column(
			nullable = false,
			length = 50
			)
	private String category;
	
	@Column(
			nullable = false,
			columnDefinition = "numeric"
			)
	private BigDecimal cost;

}
