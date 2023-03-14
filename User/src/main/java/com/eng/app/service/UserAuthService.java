package com.eng.app.service;

import java.util.Optional;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eng.app.controller.UserAuthController;
import com.eng.app.model.User;
import com.eng.app.repository.UserRepository;
import com.eng.app.utility.AuthConstants;
import com.eng.app.utility.PasswordEncryption;
import com.eng.app.validator.UserValidator;

@Service
public class UserAuthService {

	@Autowired
	UserRepository repo;

	@Autowired
	UserValidator val;

	@Autowired
	PasswordEncryption encoder;

	public boolean loginByEmail(String email, String password, HttpServletResponse response) {

		

		if (val.validateEmail(email) == false)
			return false;
		else {

			Optional<User> user = repo.findByEmail(email);
			if (!user.isEmpty()) {
				// Is the password provided from the user matching the one stored in the db?
				boolean matchingPassword = encoder.matches(password, user.get().getHashed_password());
				if (matchingPassword == true) {
					// Generate the session cookies needed
					generateSession(response, user);
					return true;

				}
			}
			return false;
		}
	}

	public boolean validateCookies(Cookie auth_cookie, Cookie role_cookie) {

		if (auth_cookie == null || role_cookie == null)
			return false;
		
		if(auth_cookie != null && auth_cookie.getValue() == "")
			return false;
		if(role_cookie != null && role_cookie.getValue() == "")
			return false;
		

		Integer auth_cookie_value = Integer.valueOf(auth_cookie.getValue());
		Optional<User> user = repo.findById(auth_cookie_value);
		if (!user.isEmpty()) {
			String role_cookie_value = role_cookie.getValue();
			if (user.get().getRole().toString().equals(role_cookie_value)) {
				return true;
			}
		}
		return false;
	}

	private void generateSession(HttpServletResponse response, Optional<User> user) {
		String user_id = user.get().getId().toString();
		generateCookie(AuthConstants.AUTHENTICATION_COOKIE, user_id, response);
		String user_role = user.get().getRole().name();
		generateCookie(AuthConstants.USER_ROLE, user_role, response);
	}

	public boolean updateUserPassword(String email, String oldPassword, String newPassword) {

		if (val.validateEmail(email) == false || val.validatePassword(newPassword) == false
				|| val.validatePassword(oldPassword) == false)
			return false;

		Optional<User> user = repo.findByEmail(email);
		if (!user.isEmpty()) {
			boolean changePassword = encoder.matches(oldPassword, user.get().getHashed_password());
			if (changePassword) {
				String password = encoder.encode(newPassword);
				return repo.updateUserPassword(password) != 0 ? true : false;
			}
		}

		return false;
	}

	public boolean generateCookie(String cookie_name, String cookie_value, HttpServletResponse response) {

		if (cookie_value != null || cookie_value != ""){
			Cookie cookie = new Cookie(cookie_name, cookie_value);
			cookie.setHttpOnly(true);
			response.addCookie(cookie);
			return true;
		}
		return false;
	}

	public void logoutUser(Cookie cookie, HttpServletResponse response) {
		cookie.setMaxAge(0);
		response.addCookie(cookie);

	}

	public boolean loginBySession(Cookie role_cookie, Cookie auth_cookie, HttpServletResponse response) {
		if (validateCookies(auth_cookie, role_cookie) == true)
			return true;
		return false;
	}

}
