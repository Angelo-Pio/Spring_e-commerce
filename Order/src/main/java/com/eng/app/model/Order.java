package com.eng.app.model;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Table(name = "orders")
@AllArgsConstructor
@NoArgsConstructor
public class Order {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@NotEmpty
	private Integer user_id;
//	
//	@NotEmpty
//	@OneToMany(mappedBy = "cart.id")
//	private List<Cart> cart_id;
//	
	@Temporal(TemporalType.TIMESTAMP)
	@NotBlank	
	private Date placed_at;
	
	@Column(columnDefinition = "float default 0.0")
	private Float total;
	
}
