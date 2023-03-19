package com.eng.app.dto;

import java.math.BigDecimal;

import org.springframework.stereotype.Component;

import com.eng.app.models.Product;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Component
public class ProductDto {
	
	public static final int MAX_DESCRIPTION_LEN = 500;
	
	private String name;
	
	private String description;

	private Integer quantity;
	
	private String category;
	
	private Float cost;
	
	
	
}
