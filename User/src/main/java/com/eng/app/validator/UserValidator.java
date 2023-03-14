package com.eng.app.validator;

import java.util.regex.Pattern;

import org.springframework.stereotype.Component;

import com.eng.app.dto.InputUserDto;
import com.eng.app.model.UserRole;

/**
 * 
 * TODO Launch exception in order to report which attributes has errors
 * 
 * @author angelo
 *
 */
@Component
public class UserValidator {

	public boolean validateDto(InputUserDto dto) {

		if (dto.getName() == null) {
			return false;
		} else if (dto.getEmail() == null) {
			return false;
		} else if (!validateEmail(dto.getEmail())) {
			return false;
		} else if (dto.getPhoneNumber() != null && !validatePhoneNumber(dto.getPhoneNumber()))
			return false;
		else if (dto.getSurname() == null)
			return false;
		/*
		 * else if (dto.getAddress() == null) return false;
		 */
		else if ((dto.getPassword() == null || dto.getMatching_password() == null)
				|| (dto.getPassword().compareTo(dto.getMatching_password()) != 0) || dto.getPassword().length() < 8) {
			return false;
		}
		/*
		 * else if(dto.getRole() != null && !validRole(dto.getRole())) { return false; }
		 */
		else {
			return true;
		}
	}

	public boolean validateEmail(String email) {
		if (email == null)
			return false;
		Pattern emailPattern = Pattern.compile("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$");
		return emailPattern.matcher(email).find();
	}

	public boolean validatePhoneNumber(String phoneNumber) {
		if (phoneNumber == null)
			return false;
		Pattern phonePattern = Pattern.compile("^[\\+]?[(]?[0-9]{3}[)]?[-\\s\\.]?[0-9]{3}[-\\s\\.]?[0-9]{4,6}$");
		return phonePattern.matcher(phoneNumber).find();

	}

	public boolean validateAddress(String address) {
		// TODO Auto-generated method stub
		return address != null;
	}

	public boolean validateRole(UserRole role) {
		if (	role.equals(UserRole.ADMIN)
				|| role.equals(UserRole.ADMIN) 
				|| role.equals(UserRole.USER)) {
			return true;
		}
		return false;
	}

//	TODO set password minimum requirements
	public boolean validatePassword(String password) {
		return password != null;
	}

}
