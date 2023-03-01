package com.eng.app.service;

import java.math.BigDecimal;
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
	
	public List<Product> getAllProducts() {
		return repo.findAll();
	}

	public Optional<Product> getOneProduct(String name) {
		Optional<Product> res = repo.getByName(name);
		if (res.isEmpty()) {
			return Optional.empty();
		} else {
			return Optional.of(res.get());
		}
	}

	public Optional<Product> createOneProduct(ProductDto p) {
		if (validator.validateDto(p)) {
			System.out.println("Here");
			if (!repo.existsByName(p.getName())) {
				Product modelProduct = mapper.fromDtoToModel(p);
				return Optional.of(repo.save(modelProduct) ) ;
			}
		}
		return Optional.empty();

	}

	public Optional<Product> destroyProduct(String name) {
		Optional<Product> product = repo.findByName(name);
		if (!product.isEmpty()) {
			repo.deleteByName(name);
			return Optional.of(product.get());
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
