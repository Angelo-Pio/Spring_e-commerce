package com.eng.app.controller;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.reactive.function.client.WebClient;

import com.eng.app.dto.ProductDto;

@Controller
public class UserController {

	@GetMapping("/")
	public String home(Model model) {
		model.addAttribute("message","Hello World");
		
		
		ProductDto products[]= WebClient.create()
				.get()
				.uri("http://localhost:8080/product/showAll")
				.retrieve()
				.bodyToMono(ProductDto[].class)
				.block()
				
				;
		model.addAttribute("products", products);
				
		
		return "index";
	}
}
