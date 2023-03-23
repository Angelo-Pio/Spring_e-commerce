package com.eng.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

import reactor.core.publisher.Mono;

@RestController
public class ApiController {

	@Autowired
	WebClient webClient;
	
	@GetMapping("/helloApi")
	public String home() {
		return webClient.get().uri("http://localhost:8082/api/user/helloApi")
				.retrieve()
				.bodyToMono(String.class)
				.block()
				;
		
	}
}
