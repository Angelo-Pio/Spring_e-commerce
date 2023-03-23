package com.eng.app.mapper;

import org.springframework.stereotype.Component;

import com.eng.app.dto.ProductDto;
import com.eng.app.models.Product;

@Component
public class ProductMapper implements ProductMapperInterface{

	@Override
	public Product fromDtoToModel(ProductDto dto) {
		new Product();
		return Product.builder()
				.name(dto.getName())
				.description(dto.getDescription())
				.category(dto.getCategory())
				.cost(dto.getCost())
				.quantity(dto.getQuantity())
				.build();
	}

	@Override
	public ProductDto fromModelToDto(Product product) {
		new ProductDto();
		return ProductDto.builder()
				.id(product.getProduct_id())
				.name(product.getName())
				.description(product.getDescription())
				.category(product.getCategory())
				.cost(product.getCost())
				.quantity(product.getQuantity())
				.build();
	}
	
	
	
	
}
