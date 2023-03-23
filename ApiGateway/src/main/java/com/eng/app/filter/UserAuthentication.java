package com.eng.app.filter;

import org.hibernate.validator.internal.metadata.aggregated.rule.ReturnValueMayOnlyBeMarkedOnceAsCascadedPerHierarchyLine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.web.WebProperties.Resources.Chain;
import org.springframework.http.HttpCookie;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ServerWebExchange;

import reactor.core.publisher.Mono;
import reactor.netty.udp.UdpServer;

public class UserAuthentication {

	private static final String AUTH_COOKIE = "AUTHENTICATION_COOKIE";

	private static final String ROLE_COOKIE = "USER_ROLE";
	
	@Value("${com.service.user.url}")
	private String user_url;

//	@Autowired
	private WebClient webClient = WebClient.builder().build();

	public boolean areCookiesPresent(ServerWebExchange exchange) {

		HttpCookie cookie = getCookie(exchange, ROLE_COOKIE);
		if (cookie == null)
			return false;
		cookie = getCookie(exchange, AUTH_COOKIE);
		if (cookie == null)
			return false;
		return true;
	}

	private HttpCookie getCookie(ServerWebExchange exchange, String cookie_name) {
		return exchange.getRequest().getCookies().getFirst(cookie_name);
	}

	
	// Mono produce eccessivi problemi 
	public boolean areCookiesValid(ServerWebExchange exchange) {

		if (areCookiesPresent(exchange)) {

			Boolean res = webClient.get().uri(user_url+"/auth/login")
					.cookie(AUTH_COOKIE, getCookie(exchange, AUTH_COOKIE).getValue())
					.cookie(ROLE_COOKIE, getCookie(exchange, ROLE_COOKIE).getValue()).retrieve()
					.bodyToMono(Boolean.class)
					.block();
//					.onErrorReturn(false)
					

			return res != null ? res.booleanValue() : false;

		} else {
			return false;

		}

	}

}
