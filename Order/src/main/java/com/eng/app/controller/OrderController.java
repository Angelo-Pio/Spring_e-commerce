package com.eng.app.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.eng.app.model.Order;
import com.eng.app.service.OrderService;
import com.eng.app.utility.ControllerUtilities;

@RestController
@RequestMapping("/api/order")
public class OrderController {
	@Autowired
	OrderService service;
	
	@Autowired
	ControllerUtilities ut;
	
	@PostMapping("create")
	public ResponseEntity<Boolean> create( @Valid @RequestParam("user_id") Integer user_id){
		Boolean resp = service.create(user_id);
		return ut.responseStatus(resp, HttpStatus.CREATED, HttpStatus.BAD_REQUEST);
		
	}
	
	@GetMapping("/{user_id}/showAll")
	public ResponseEntity<List<Order>> showAll(@PathVariable Integer user_id){
		return new ResponseEntity<>(service.showAll(user_id),HttpStatus.OK);
	}
	
	@GetMapping("showAll")
	public ResponseEntity<List<Order>> showAll(){
		return new ResponseEntity<>(service.showAll(),HttpStatus.OK);
	}
	
	@ExceptionHandler({MethodArgumentNotValidException.class})
	public ResponseEntity<Boolean> handleParametersException(){
		return new ResponseEntity<Boolean>(false,HttpStatus.BAD_REQUEST);
	}
}
