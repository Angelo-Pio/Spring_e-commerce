package com.eng.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.eng.app.service.UserAuthService;

@RestController
@RequestMapping("/api/user/auth")
public class UserAuthController {

	@Autowired
	UserAuthService service;
	
//	@GetMapping("login")
//	public boolean login(@RequestParam("email") String email, @RequestParam("password") String password) {
//		return service.loginUser(email,password);
//	}
	
	@PatchMapping("updatePassword")
	public boolean updatePassword(@RequestParam("email") String email, 
			@RequestParam("oldPassword") String oldPassword,
			@RequestParam("newPassword") String newPassword) {
		return service.updateUserPassword(email, oldPassword, newPassword);
	}
	
}
