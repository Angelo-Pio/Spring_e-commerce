package com.eng.app.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.eng.app.filter.UserAuthentication;
import com.eng.app.filter.UserAuthenticationFilter;

@Configuration
public class GatewayRouteConfig {

	@Value("${com.service.user.url}")
	private String user_url ;
	
	
	@Value("${com.service.product.url}")
	private String product_url ;
	
	@Value("${com.service.order.url}")
	private String order_url ;
	
	
	 @Bean
	 public RouteLocator routeLocator(RouteLocatorBuilder builder) {
		 return builder.routes()
				 // All of these routes have to be authenticated
				 .route("user-service", r -> r.path("/user/**")
						 .filters(w -> w.rewritePath("/user/(?<segment>/?.*)", "/api/user/$\\{segment}")
						 )
						 .uri(user_url)
						 )
				 .route("only-auth", r -> r.path("/auth/**")
//						 .and().not(t -> t.path("/auth/login").or().path("auth/logout"))
						
						 //TODO escludere /auth/login e /auth/logout
						 .filters(w ->  w.rewritePath("/auth/(?<segment>/?.*)", "/api/auth/$\\{segment}")
//								 .filter(new UserAuthenticationFilter().apply(new UserAuthentication()))
								 )
						 
						 .uri(user_url)
						 )
				 				 
				 .route("order", r ->  r.path("/order/**")
						 .filters(w -> w.rewritePath("/order/(?<segment>/?.*)", "/api/order/$\\{segment}")
								 .filter(new UserAuthenticationFilter().apply(new UserAuthentication()))
						 )
						 .uri(order_url)	 
						 )
				 .route("cart", r -> r.path("/cart/**")
						 .filters(w -> w.rewritePath("/cart/(?<segment>/?.*)", "/api/cart/$\\{segment}")
								 .filter(new UserAuthenticationFilter().apply(new UserAuthentication()))
						 )
						 .uri(order_url)	 
						 )
				 .route("inventory", r -> r.path("/product/**")
						 .filters(w -> w.rewritePath("/product/(?<segment>/?.*)", "/api/product/$\\{segment}")
						 )
						 .uri(product_url)	 
						 )
						 
						 /* implement role based routes for admins and users
						  * eg: /api/product/showAll -> no auth 
						  *    /api/product/(create/update/delete) -> admin
						  *    /api/product/(decrease/increaseQUantity) -> auth
						  */
						 
						 .build();
				 
	 }


	
}
