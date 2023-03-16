package com.eng.app.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.eng.app.model.Cart;
import com.eng.app.repository.CartRepository;

@RestController
@RequestMapping("/cart")
public class CartController {

	@Autowired
	CartRepository repo;
	
	@PostMapping("create")
	public ResponseEntity<Boolean> create(@Valid @RequestBody Cart cart){
		
		Cart resp = repo.save(cart);
		if(resp == null) {
			return new ResponseEntity<Boolean>(false,HttpStatus.BAD_REQUEST);
		}else {
			return new ResponseEntity<Boolean>(true,HttpStatus.OK);
		}
	}
	
	@GetMapping("showAll")
	public ResponseEntity<List<Cart>> showAll(){
		return new ResponseEntity<>(repo.findAll(),HttpStatus.OK);
	}
	
	@ExceptionHandler({MethodArgumentNotValidException.class})
	public ResponseEntity<Boolean> handleParametersException(){
		return new ResponseEntity<Boolean>(false,HttpStatus.BAD_REQUEST);
	}
}
