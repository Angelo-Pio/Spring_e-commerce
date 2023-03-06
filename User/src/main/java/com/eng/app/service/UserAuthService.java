package com.eng.app.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.www.BasicAuthenticationEntryPoint;
import org.springframework.stereotype.Service;

import com.eng.app.model.User;
import com.eng.app.repository.UserRepository;
import com.eng.app.validator.UserValidator;

@Service
public class UserAuthService {

	@Autowired
	UserRepository repo;

	@Autowired
	UserValidator val;

	@Autowired
	PasswordEncoder encoder;

	
	public boolean loginUser(String email, String password) {
				
		if (val.validateEmail(email) == false)
			return false;

		Optional<User> user = repo.findByEmail(email);
		if (!user.isEmpty()) {
			return encoder.matches(password, user.get().getHashed_password());
		}

		return false;
	}

	public boolean updateUserPassword(String email, String oldPassword, String newPassword) {
		
		if (val.validateEmail(email) == false || val.validatePassword(newPassword) == false || val.validatePassword(oldPassword) == false)
			return false;

		Optional<User> user = repo.findByEmail(email);
		if (!user.isEmpty()) {
			boolean changePassword = encoder.matches(oldPassword, user.get().getHashed_password());
			if(changePassword) {
				String password = encoder.encode(newPassword);
				return repo.updateUserPassword(password) != 0 ? true : false;
			}
		}

		return false;
	}
}
