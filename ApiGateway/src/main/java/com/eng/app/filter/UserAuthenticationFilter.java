package com.eng.app.filter;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Optional;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.OrderedGatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.cloud.gateway.filter.factory.RedirectToGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

import static org.springframework.cloud.gateway.support.ServerWebExchangeUtils.GATEWAY_REQUEST_URL_ATTR;
import static org.springframework.cloud.gateway.support.ServerWebExchangeUtils.addOriginalRequestUrl;
import static org.springframework.cloud.gateway.support.ServerWebExchangeUtils.setResponseStatus;

@Slf4j
@Component
public class UserAuthenticationFilter extends AbstractGatewayFilterFactory<UserAuthentication> {

	public UserAuthenticationFilter() {
		super(UserAuthentication.class);
	}

	@Override
	public GatewayFilter apply(UserAuthentication authenticator) {

		return (exchange, chain) -> {

			boolean areCookiesPresent = authenticator.areCookiesPresent(exchange);
			boolean areCookiesValid = authenticator.areCookiesValid(exchange);
			
			if (areCookiesPresent == false || areCookiesValid == false) {
				log.info("session authentication through cookies : NOT OK -> redirecting to loginPage");
				
				var response = exchange.getResponse();
				response.setStatusCode(HttpStatus.PERMANENT_REDIRECT);
				response.getHeaders().set("Location", "/user/loginPage");
				return response.setComplete();
				
				
			}

			log.info("session authentication through cookies : OK");
			
			return chain.filter(exchange);
			
		};

	}

	
}
