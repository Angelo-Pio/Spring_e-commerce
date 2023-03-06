package com.eng.app.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
@Configuration
public class WebSecurityConfig {

	@Autowired
	DataSource datasource;
	
	private static final String[] WHITE_LIST_ADMIN_URLS = {
		"/create",
		"/destroy",
		"/update"
	};
	
	private static final String[] WHITE_LIST_USER_URLS = {
			"/updatePassword"
		};
	
	
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder(11);
	}
	
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
		http
			.cors()
			.and()
			.csrf()
			.disable()
			.authorizeHttpRequests()
			.antMatchers(  WHITE_LIST_ADMIN_URLS ).hasRole("ADMIN")
			.antMatchers(WHITE_LIST_USER_URLS).hasRole("USER")
			.anyRequest().permitAll()
			
			;
		
		
		return http.build();
		
		
	}
	
	@Bean
	public UserDetailsManager userDetailsManager() {
		JdbcUserDetailsManager manager = new JdbcUserDetailsManager(datasource);
		
		manager.setUsersByUsernameQuery("select email from users where email = ?");
		return manager;
				
				
	}
	
	
	
}
