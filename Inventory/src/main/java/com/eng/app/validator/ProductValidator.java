package com.eng.app.validator;

import java.math.BigDecimal;

import org.springframework.stereotype.Component;

import com.eng.app.dto.ProductDto;
import com.eng.app.models.Product;

@Component
public class ProductValidator {

	public boolean validateDto(ProductDto product) {
		// TODO Auto-generated method stub

		if (product.getName() == null || product.getName().length() > Product.MAX_NAME_LEN) {
			return false;
		} else if (product.getDescription() == null || product.getDescription().length() > Product.MAX_DESCRIPTION_LEN) {
			return false;
		} else if (product.getCost() == null ||  product.getCost()  < 0) {
			return false;
		} else if (product.getQuantity() == null ||  product.getQuantity().compareTo(0) < 0) {
			return false;
		} else if (product.getCategory() == null ) {
			return false;
		} else {
			return true;
		}
	}
	
	
	/*
	 * These methods can be handy if you want to implement single attributes checks
	 */
	public boolean hasName(ProductDto p) {
		return p.getName() != null ? true : false; 
	}

	public boolean validateQuantity(Integer quantity) {
		
		return quantity >= 0;
	}

	public boolean validateCost(Float cost) {
		return cost > 0;
	}

	public boolean validateCategory(String category) {
		return category != null ;
	}

	public boolean validateDescription(String description) {
		return description.length() <= Product.MAX_DESCRIPTION_LEN;
	}


}
