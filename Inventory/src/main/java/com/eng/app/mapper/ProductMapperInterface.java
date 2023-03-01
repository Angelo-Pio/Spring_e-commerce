package com.eng.app.mapper;

import com.eng.app.dto.ProductDto;
import com.eng.app.models.Product;

public interface ProductMapperInterface {
	Product fromDtoToModel(ProductDto dto);
	ProductDto fromModelToDto(Product product);
}
