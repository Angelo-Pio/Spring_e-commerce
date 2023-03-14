package com.eng.app.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.eng.app.filter.UserAuthentication;
import com.eng.app.filter.UserAuthenticationFilter;

@Configuration
public class GatewayRouteConfig {

	 @Bean
	public RouteLocator routeLocator(RouteLocatorBuilder builder) {
		
		return builder.routes()
				// All of these routes have to be authenticated
				.route("user-service", r -> r.path("/user/**")
						.filters(w -> w.rewritePath("/user/(?<segment>/?.*)", "/api/user/$\\{segment}"))
						.uri("http://localhost:8082")
						)
//				.route("user-login", r -> r.path("/auth/login")
//						.filters(w ->  w.rewritePath("/auth/login", "/api/auth/login")
//								)
//						.uri("http://localhost:8082")
//					  )
//				.route("user-logout", r -> r.path("/auth/logout")
//						.filters(w ->  w.rewritePath("/auth/logout", "/api/auth/logout")
//								)
//						.uri("http://localhost:8082")
//					  )
				.route("only-auth", r -> r.path("/auth/**")
						//TODO escludere /auth/login e /auth/logout
						.filters(w ->  w.rewritePath("/auth/(?<segment>/?.*)", "/api/auth/$\\{segment}")
								.filter(new UserAuthenticationFilter().apply(new UserAuthentication()))
								)
						.uri("http://localhost:8082")
					  )
				/* implement role based routes for admins and users
				* eg: /api/product/showAll -> no auth 
 				*    /api/product/(create/update/delete) -> admin
 				*    /api/product/(decrease/increaseQUantity) -> auth
				*/
				
				.build();
		
	}
	


	
}
