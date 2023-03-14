package com.eng.app.filter;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Optional;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import reactor.core.publisher.Mono;

@Component
public class UserAuthenticationFilter extends AbstractGatewayFilterFactory<UserAuthentication> {

	public UserAuthenticationFilter() {
		super(UserAuthentication.class);
	}

	@Override
	public GatewayFilter apply(UserAuthentication authenticator) {

		return (exchange, chain) -> {

			// a cookie is valid when it matches an entry in the
			// user service db with an expiration date still not reached
			boolean areCookiesPresent = authenticator.areCookiesPresent(exchange);
			
			//block non può essere usato, viene riportato un errore ma ho necessità
			// di estrarre il valore boolean da areCookiesValid
			Boolean areCookiesValid = authenticator.areCookiesValid(exchange).block();

			if (areCookiesPresent == false || areCookiesValid == false) {

				URI redirectUri;
				try {
					// TODO make it possible to use Spring gateway path rewriting from a filter
					redirectUri = new URI("http://localhost:8080/user/loginPage");
				} catch (URISyntaxException e) {
					e.printStackTrace();
					return null;
				}

				exchange.mutate().request(redirectToLogin(exchange, redirectUri)).build();

//				URI loginUri = UriComponentsBuilder.fromUriString("/user/loginPage").build().toUri();
//				ServerHttpResponse response = exchange.getResponse();
//				response.setStatusCode(HttpStatus.SEE_OTHER);
//				response.getHeaders().setLocation(loginUri);
//				return response.setComplete();

			}

			return chain.filter(exchange);

		};

	}

	private org.springframework.http.server.reactive.ServerHttpRequest redirectToLogin(ServerWebExchange exchange,
			URI uri) {
		return exchange.getRequest().mutate().uri(uri).method(HttpMethod.GET).build();

	}
}
