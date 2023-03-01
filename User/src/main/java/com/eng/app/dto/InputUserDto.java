package com.eng.app.dto;

import org.springframework.stereotype.Component;

import com.eng.app.model.UserRole;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Component
public class InputUserDto {

	private String name;
	
	private String surname;
	
	private String email;
	
	private String address;

	private String phoneNumber;

	private String password;
	
	private String matching_password;
	
	private UserRole role;
}
