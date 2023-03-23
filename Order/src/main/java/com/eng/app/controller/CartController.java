package com.eng.app.controller;

import java.util.Date;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.eng.app.model.Cart;
import com.eng.app.service.CartService;
import com.eng.app.utility.ControllerUtilities;

@RestController
@RequestMapping("/api/cart")
public class CartController {

	@Autowired
	CartService service;
	
	@Autowired
	ControllerUtilities ut;

//	CREATE
	@PostMapping("create")
	public ResponseEntity<Boolean> create(@Valid @RequestBody Cart cart) {

		Boolean resp = service.create(cart);
		return ut.responseStatus(resp, HttpStatus.CREATED, HttpStatus.BAD_REQUEST);
	}

//	READ
	@GetMapping("showAll")
	public ResponseEntity<List<Cart>> showAll(@RequestParam("user_id") Integer user_id) {
		return new ResponseEntity<>(service.showAll(user_id), HttpStatus.OK);
	}

//	UPDATE
	@PostMapping("/updateQuantity")
	public ResponseEntity<Boolean> increaseQuantity(@RequestParam("quantity") Integer quantity,
			@RequestParam("cart_id") Integer cart_id) {
		Boolean res = service.increaseQuantity(Integer.valueOf(cart_id), quantity);
		return ut.responseStatus(res, HttpStatus.OK, HttpStatus.BAD_REQUEST);
	}

//	DELETE
	@DeleteMapping("delete")
	public ResponseEntity<Boolean> delete(@RequestParam("cart_id") Integer cart_id) {
		Boolean res = service.delete(cart_id);
		return ut.responseStatus(res, HttpStatus.OK, HttpStatus.BAD_REQUEST);
	}

//	CONFIRM
	
	
}
