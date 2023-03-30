package com.eng.app.filter;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Optional;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.OrderedGatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.cloud.gateway.filter.factory.RedirectToGatewayFilterFactory;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

import static org.springframework.cloud.gateway.support.ServerWebExchangeUtils.GATEWAY_REQUEST_URL_ATTR;
import static org.springframework.cloud.gateway.support.ServerWebExchangeUtils.addOriginalRequestUrl;

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
				log.info("session authentication through cookies : NOT OK -> redirecting");
//				URI redirectUri;
//											
//				redirectUri = URI.create("http://localhost:8080/user/loginPage");
//				
//				ServerHttpRequest request = exchange
//						.getRequest()
//						.mutate()
//						.uri(redirectUri)
//						.method(HttpMethod.GET)
//						.build();
//				
				
				var resp = exchange.getResponse();
				resp.setStatusCode(HttpStatus.UNAUTHORIZED);
				return resp.setComplete(); 
//						.mutate()
//						.request(request)
//						.build();
				
//				log.info(request.getURI().toString());

//				mutatedExchange.getAttributes().put(GATEWAY_REQUEST_URL_ATTR, request.getURI());
//				
//				return chain.filter(mutatedExchange);
				
			}

			log.info("session authentication through cookies : OK");
			return chain.filter(exchange);
			
		};

	}

	
}
