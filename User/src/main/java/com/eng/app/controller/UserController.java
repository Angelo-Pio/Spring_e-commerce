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
import org.springframework.web.reactive.function.client.WebClient;

import com.eng.app.dto.InputUserDto;
import com.eng.app.dto.OutputUserDto;
import com.eng.app.dto.ProductDto;
import com.eng.app.service.UserService;

/**
 * Rest controller to handle requests
 * ! This controller will use a different approach, unlike ProductController
 * this class will implement the mapping into the service and will work 
 * only using Dtos
 * @author angelo
 *
 */
@RestController
@RequestMapping("/api/user/")
public class UserController {

	@Autowired
	private UserService service;
	
	@Autowired
	private WebClient webClient;
	
	// POST 
	@PostMapping("create")
	public ResponseEntity<OutputUserDto> create(@RequestBody InputUserDto dto) {
		Optional<OutputUserDto> user = service.createOneUser(dto);
		return HttpResponse(user, HttpStatus.CREATED, HttpStatus.BAD_REQUEST);
	}
	
	// GET 
	
	@GetMapping("showOne")
	public ResponseEntity<OutputUserDto> showOne(@RequestParam("email") String email){
		Optional<OutputUserDto> user = service.getOneUser(email);
		return HttpResponse(user, HttpStatus.FOUND, HttpStatus.BAD_REQUEST);
	}
	
	@GetMapping("showAll")
	public ResponseEntity<List<OutputUserDto>> showAll(){
		Optional<List<OutputUserDto>> list = service.getAllUsers();
		return new ResponseEntity<List<OutputUserDto>>(list.get(),HttpStatus.OK);
	}
	

	//DELETE
	@DeleteMapping("destroy")
	public ResponseEntity<OutputUserDto> destroy(@RequestParam("email") String email){
		Optional<OutputUserDto> user = service.destroyOneUser(email);
		return HttpResponse(user, HttpStatus.OK, HttpStatus.BAD_REQUEST);
	}
	
	//UPDATE
	
	@PatchMapping("update")
	public ResponseEntity<OutputUserDto> update(@RequestBody InputUserDto dto){
		Optional<OutputUserDto> user = service.updateUserCredentials(dto);
		return HttpResponse(user, HttpStatus.OK, HttpStatus.BAD_REQUEST);
	}
	
	
	//Interaction with product service, test only
	@GetMapping("products")
	public ProductDto[] showProducts(){
		return webClient
			.get()
			.uri("http://localhost:8081/api/product/showAll")
			.retrieve()
			.bodyToMono(ProductDto[].class)
			.block();
		
	}
	
	// Private Methods
	/**
	 * Utility method to build a response depending on the result of a query to the
	 * db
	 * 
	 * @param prod
	 * @return
	 */
	private ResponseEntity<OutputUserDto> HttpResponse(Optional<OutputUserDto> user, HttpStatus success, HttpStatus failure) {
		ResponseEntity<OutputUserDto> response;
		if (user.isEmpty()) {
			response = new ResponseEntity<OutputUserDto>(failure);
		} else {
			response = new ResponseEntity<OutputUserDto>(user.get(),success);
		}
		return response;
	}

}
