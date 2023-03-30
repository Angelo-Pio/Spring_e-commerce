package com.eng.app.filter;

import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpCookie;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ServerWebExchange;

import lombok.extern.slf4j.Slf4j;
@Slf4j
@Component
public class UserAuthentication {

	private static final String AUTH_COOKIE = "AUTHENTICATION_COOKIE";

	private static final String ROLE_COOKIE = "USER_ROLE";
	
	@Value("${com.service.user.url}")
	private String user_url;

//	@Autowired
	private WebClient webClient = WebClient.builder().build();

	public boolean areCookiesPresent(ServerWebExchange exchange) {

		 MultiValueMap<String, HttpCookie> cookies = exchange.getRequest().getCookies();
		
		 if(cookies.containsKey(AUTH_COOKIE) && cookies.containsKey(ROLE_COOKIE)) {
			 return true;
		 }

		return false;
	}

	private HttpCookie getCookie(ServerWebExchange exchange, String cookie_name) {
		
		log.info("Logging cookies");
		
		log.info(exchange.getRequest().getCookies().getFirst(cookie_name).toString());
		
		return exchange.getRequest().getCookies().getFirst(cookie_name);
	}

	
	// Mono produce eccessivi problemi 
	public boolean areCookiesValid(ServerWebExchange exchange) {

		if (areCookiesPresent(exchange)) {

			Boolean res = webClient
					.get()
					.uri("http://localhost:8082/api"+"/auth/login")
					.cookie(AUTH_COOKIE, getCookie(exchange, AUTH_COOKIE).getValue())
					.cookie(ROLE_COOKIE, getCookie(exchange, ROLE_COOKIE).getValue())
					.retrieve()
					.bodyToMono(Boolean.class)
					.onErrorReturn(Boolean.FALSE)
					.block()
					;
					

			return res != null ? res.booleanValue() : false;

		} else {
			return false;

		}

	}

}
