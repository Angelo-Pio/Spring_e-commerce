package com.eng.app.utility;

import java.util.Base64;

import org.springframework.stereotype.Component;

@Component
public class PasswordEncryption {

	public  String encode(String originalInput) {
		String encodedString = Base64.getEncoder().encodeToString(originalInput.getBytes());
		return encodedString;
	}
	
	public  boolean matches(String input, String password) {
		return encode(input).equals(password);
	}
}
