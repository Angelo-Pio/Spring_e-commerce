package com.eng.app.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eng.app.dto.ProductDto;
import com.eng.app.mapper.ProductMapper;
import com.eng.app.models.Product;
import com.eng.app.repository.ProductRepository;
import com.eng.app.validator.ProductValidator;

@Service
public class ProductService {

	@Autowired
	private ProductRepository repo;

	@Autowired
	private ProductValidator validator;

	@Autowired
	private ProductMapper mapper;
	
	public Optional<List<ProductDto>> getAllProducts() {
		
		ArrayList<Product> list = (ArrayList<Product>) repo.findAll();
		
		ArrayList<ProductDto> responseList = new ArrayList<>(list.size());
		for(Product p : list) {
			ProductDto dto = mapper.fromModelToDto(p);
			responseList.add(dto);
		}
		
		return Optional.of(responseList);
	}

	public Optional<ProductDto> getOneProduct(String name) {
		Optional<Product> res = repo.getByName(name);
		if (res.isEmpty()) {
			return Optional.empty();
		} else {
			return Optional.of(mapper.fromModelToDto(res.get()));
		}
	}

	public Optional<ProductDto> createOneProduct(ProductDto p) {
		if (validator.validateDto(p)) {
			System.out.println("Here");
			if (!repo.existsByName(p.getName())) {
				Product modelProduct = mapper.fromDtoToModel(p);
				return Optional.of(mapper.fromModelToDto(repo.save(modelProduct)) ) ;
			}
		}
		return Optional.empty();

	}

	public Optional<ProductDto> destroyProduct(String name) {
		Optional<Product> product = repo.findByName(name);
		if (!product.isEmpty()) {
			repo.deleteByName(name);
			return Optional.of(mapper.fromModelToDto(product.get()));
		} else {
			return Optional.empty();
		}

	}

	public boolean updateProduct(ProductDto p) {

		// We at least need a name!
		if (!validator.hasName(p))
			return false;

		// Search product
		Optional<Product> productOpt = repo.findByName(p.getName());

		if (productOpt.isEmpty()) {
			return false;
		}

		// Get it
		Product product = productOpt.get();

		// Update single attributes!

		String description = p.getDescription();
		BigDecimal cost = p.getCost();
		String category = p.getCategory();
		Integer quantity = p.getQuantity();

		if (description != null) {
			if (!validator.validateDescription(description))
				return false;
			product.setDescription(description);
//			repo.updateDescription(description);
		}
		if (category != null) {
			if (validator.validateCategory(category) == false)
				return false;
			product.setCategory(category);
		}
		if (cost != null) {
			if (!validator.validateCost(cost))
				return false;
			product.setCost(cost);
		}
		if (quantity != null) {
			if (!validator.validateQuantity(quantity))
				return false;
			product.setQuantity(quantity);

		}
		
		repo.save(product);
		return true;

	}

}
