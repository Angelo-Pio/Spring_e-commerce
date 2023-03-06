package com.eng.app.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.eng.app.dto.ProductDto;
import com.eng.app.service.ProductService;

@RestController
@RequestMapping("/api/product")
public class ProductController {
	
	@Autowired
	private ProductService service;
	
	//! GET
	
	@GetMapping("showAll")
	public ResponseEntity<List<ProductDto>> showAll() {
		
		Optional<List<ProductDto>> responseList = service.getAllProducts();
		return new ResponseEntity<>(responseList.get(),HttpStatus.OK);
	}
	
	/**
	 * Returns the product requested if present in the system else returns null
	 * @param name
	 * @return
	 */
	@GetMapping("showOne")
	public ResponseEntity<ProductDto> showOne(@RequestParam("name" )String name){
		
		Optional<ProductDto> prod = service.getOneProduct(name);
		 return responseQuery(prod,HttpStatus.FOUND,HttpStatus.NOT_FOUND);
	}

	
	// POST
	
	/**
	 * Register new product into the system if not yet registered
	 * @param p
	 * @return
	 */
	@PostMapping("create")
	public ResponseEntity<ProductDto> createProduct( @RequestBody(required = true)	 ProductDto p) {
		
		Optional<ProductDto> queryResult = service.createOneProduct(p);
		ResponseEntity<ProductDto> e = responseQuery(queryResult,HttpStatus.CREATED,HttpStatus.BAD_REQUEST);
		return e;
	}
		
	//DELETE
	
	@DeleteMapping("destroy")
	public ResponseEntity<ProductDto> destroyProduct( @RequestParam("name") String name){
		
		Optional<ProductDto> queryResult = service.destroyProduct(name);
		ResponseEntity<ProductDto> e = responseQuery(queryResult,HttpStatus.OK,HttpStatus.GONE);
		return e;
		
	}
	
	
	//UPDATE
	
	/**
	 * Use a DTO to update an existing product identified by his name.
	 * Null attributes are ignored during update
	 * @param product
	 * @return
	 */
	@PatchMapping("update")
	public ResponseEntity<ProductDto> updateProductQuantity(@RequestBody ProductDto product){
		boolean queryResult = service.updateProduct(product);
		HttpStatus response = queryResult == true ? HttpStatus.OK : HttpStatus.BAD_REQUEST ;
		return new ResponseEntity<ProductDto>(response);
	}
	
	
	
	
	
	
	// Private Methods
	/**
	 * Utility method to build a response depending on the result of a query to the db
	 * @param prod
	 * @return
	 */
	private ResponseEntity<ProductDto> responseQuery(Optional<ProductDto> prod, HttpStatus success, HttpStatus failure) {
		ResponseEntity<ProductDto> response;
		if(prod.isEmpty()) {
			response = new ResponseEntity<ProductDto>(failure);
		}else {
			response = new ResponseEntity<ProductDto>(prod.get(),success);
		}
		return response;
	}
}
