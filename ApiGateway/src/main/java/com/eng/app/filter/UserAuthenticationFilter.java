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
			Mono<Boolean> areCookiesValid = authenticator.areCookiesValid(exchange);
			
			return areCookiesValid.flatMap(t -> {
				
			
			
			if (areCookiesPresent == false || t.booleanValue() == false) {

				System.out.println("Checks passed");
				URI redirectUri;
				try {
					// TODO make it possible to use Spring gateway path rewriting from a filter
					redirectUri = new URI("http://localhost:8082/api/user/loginPage");
				} catch (URISyntaxException e) {
					e.printStackTrace();
					return null;
				}

				return chain.filter(exchange.mutate().request(redirectToLogin(exchange, redirectUri)).build());
				

			}

			return chain.filter(exchange);
			});
		};

	}

	private org.springframework.http.server.reactive.ServerHttpRequest redirectToLogin(ServerWebExchange exchange,
			URI uri) {
		return exchange.getRequest().mutate().uri(uri).method(HttpMethod.GET).build();

	}
}
