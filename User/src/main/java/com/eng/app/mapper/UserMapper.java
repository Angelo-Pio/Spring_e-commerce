package com.eng.app.mapper;

import org.springframework.stereotype.Component;

import com.eng.app.dto.InputUserDto;
import com.eng.app.dto.OutputUserDto;
import com.eng.app.model.User;

@Component
public class UserMapper {

	/*
	 * TODO modify UserDto in order to contain null values for hashed_password
	 * - handle hashing with salt
	 * - static salt ?
	 * 
	 */
	public User fromDtoToModel(InputUserDto dto) {
		return User.builder()
				.name(dto.getName())
				.surname(dto.getSurname())
				.address(dto.getAddress())
				.email(dto.getEmail())
				.phoneNumber(dto.getPhoneNumber())
				.role(dto.getRole())
				.build();
	}

	public OutputUserDto fromModelToDto(User user) {
		   return OutputUserDto.builder()
				.name(user.getName())
				.surname(user.getSurname())
				.email(user.getEmail())
				.address(user.getAddress())
				.phoneNumber(user.getPhoneNumber())
				.role(user.getRole())
				.build();
				
	}

}
