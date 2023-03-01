package com.eng.app.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.eng.app.dto.InputUserDto;
import com.eng.app.dto.OutputUserDto;
import com.eng.app.mapper.UserMapper;
import com.eng.app.model.User;
import com.eng.app.model.UserRole;
import com.eng.app.repository.UserRepository;
import com.eng.app.validator.UserValidator;

@Service
public class UserService {

	@Autowired
	UserRepository repo;

	@Autowired
	UserValidator val;

	@Autowired
	UserMapper mapper;

	@Autowired
	PasswordEncoder encoder;

	/**
	 * Validate input <class> UserDto <class> dto, check if not yet present in the
	 * DB if true create new User object into the DB through mapping input dto to a
	 * User oject
	 * 
	 * @param dto
	 * @return
	 */
	public Optional<OutputUserDto> createOneUser(InputUserDto dto) {

		if (val.validateDto(dto)) {
			if (repo.existsByEmail(dto.getEmail()) == false) {

				User user = mapper.fromDtoToModel(dto);
				user.setHashed_password(encoder.encode(dto.getPassword()));

				if (dto.getRole() == null) {
					user.setRole(UserRole.GUEST);
				}
				repo.save(user);
				return Optional.of(mapper.fromModelToDto(user));

			}
		}
		return Optional.empty();

	}

	public Optional<OutputUserDto> getOneUser(String email) {

		// Should throw exception???

		if (val.validateEmail(email) == false)
			return Optional.empty();

		Optional<User> user = repo.findByEmail(email);

		if (!user.isEmpty()) {
			OutputUserDto fromModelToDto = mapper.fromModelToDto(user.get());
			return Optional.of(fromModelToDto);
		}

		return Optional.empty();

	}

	public Optional<List<OutputUserDto>> getAllUsers() {

		List<User> list = repo.findAll();
		List<OutputUserDto> returnList = new ArrayList<>(list.size());
		for (User user : list) {
			returnList.add(mapper.fromModelToDto(user));
		}
		return Optional.of(returnList);

	}

	public Optional<OutputUserDto> destroyOneUser(String email) {

		if (val.validateEmail(email) == false)
			return Optional.empty();
		Optional<User> user = repo.findByEmail(email);
		if (!user.isEmpty()) {
			repo.delete(user.get());
			return Optional.of(mapper.fromModelToDto(user.get()));
		}
		return Optional.empty();
	}

}
