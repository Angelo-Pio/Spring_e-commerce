package com.eng.app.controller;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.eng.app.service.UserAuthService;
import com.eng.app.utility.AuthConstants;

@RestController
@RequestMapping("/api/auth")
public class UserAuthController {

	

	@Autowired
	UserAuthService service;
	
	// Authentication of the user
	@GetMapping("logout")
	public void logout(
			@CookieValue(name = AuthConstants.AUTHENTICATION_COOKIE, required = false) Cookie auth_cookie,
			@CookieValue(name = AuthConstants.USER_ROLE, required = false) Cookie role_cookie,
			HttpServletResponse response) {
		service.logoutUser(auth_cookie, response); 
		service.logoutUser(role_cookie, response);
	}
	
	@GetMapping("login")
	public ResponseEntity<Boolean> login(
			@RequestParam(name = "email", required = false) String email ,
			@RequestParam(name = "password" ,required = false) String password,
			@CookieValue(name = AuthConstants.AUTHENTICATION_COOKIE, required = false) Cookie auth_cookie,
			@CookieValue(name = AuthConstants.USER_ROLE, required = false) Cookie role_cookie,
			HttpServletResponse response
			) {
		
		// accept email and password, then generate cookie for session management 
		// and return it in the response
		boolean ret = false;
		if(email  != null && password != null) {
			ret = service.loginByEmail(email, password, response);
			
			if(auth_cookie != null && role_cookie != null) {
				ret = service.loginBySession(role_cookie, auth_cookie, response);
			}
		}
		HttpStatus status = ret == true ? HttpStatus.OK : HttpStatus.BAD_REQUEST;

		return new ResponseEntity<Boolean>(ret,status);
				
	}

	
	/**
	 * check if both the cookies are correct
	 * @param auth_cookie
	 * @param role_cookie
	 * @return
	 */
	@GetMapping("areUserCookiesValid") 
	  public boolean validateSession(
			  	  @CookieValue(name = AuthConstants.AUTHENTICATION_COOKIE , required = true) Cookie auth_cookie,
				  @CookieValue(name = AuthConstants.USER_ROLE  , required = true) Cookie role_cookie
				 )
	  { 
		
		return service.validateCookies(auth_cookie, role_cookie);
	  }
	
	@PatchMapping("updatePassword")
	public boolean updatePassword(@RequestParam("email") String email, 
			@RequestParam("oldPassword") String oldPassword,
			@RequestParam("newPassword") String newPassword) {
		return service.updateUserPassword(email, oldPassword, newPassword);
	}
	
	// TODO COOKIE TESTING
	
	@GetMapping("setCookie")
	public String setCookie(HttpServletResponse response) {
		Cookie cookie = new Cookie("myCookie", "Hello_this_is_a_test_cookie");
		cookie.setHttpOnly(true);
		cookie.setComment("Comment");
//		cookie.setMaxAge(10);
		response.addCookie(cookie);
		return "done";
		
	}
	
	@GetMapping("getCookie")
	public String getCookie(HttpServletRequest request) {
		Cookie[] cookies = request.getCookies() ;
		Cookie cookie = cookies[0];
		return cookie.getComment() + cookie.getValue() + cookie.getName() ;
		
	}
}
