package com.eng.app.dto;

import org.springframework.stereotype.Component;

import com.eng.app.model.UserRole;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Setter
@Component
public class OutputUserDto {

	private String name;
	
	private String surname;
	
	private String email;
	
	private String address;

	private String phoneNumber;
	
	private UserRole role;
	
}
