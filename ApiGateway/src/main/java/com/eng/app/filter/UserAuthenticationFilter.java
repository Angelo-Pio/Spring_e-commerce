package com.eng.app.filter;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Optional;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.cloud.gateway.filter.factory.RedirectToGatewayFilterFactory;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Slf4j
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
			boolean areCookiesValid = authenticator.areCookiesValid(exchange);
			
			if (areCookiesPresent == false || areCookiesValid == false) {
				log.info("session authentication through cookies : NOT OK -> redirecting");
				URI redirectUri;
				try {
					// TODO Need to find out how to redirect to local index.html
					redirectUri = new URI("http://localhost:8082/api/user/helloApi");
				} catch (URISyntaxException e) {
					e.printStackTrace();
					return null;
				}
				ServerWebExchange mutatedExchange = exchange.mutate().request(redirectToLogin(exchange, redirectUri)).build();
				return chain.filter(mutatedExchange);
				
			}

			log.info("session authentication through cookies : OK");
			return chain.filter(exchange);
			
		};

	}

	private org.springframework.http.server.reactive.ServerHttpRequest redirectToLogin(ServerWebExchange exchange,
			URI uri) {
		return exchange.getRequest().mutate().uri(uri).build();

	}
}
