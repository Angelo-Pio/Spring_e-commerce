package com.eng.app.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Check;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "users")
@Check(constraints = "length(hashed_password) >= 8 ")
public class User {
	// TODO should implement regular expression to check correct syntax
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(length = 255, nullable = false)
	private String name;
	
	@Column(length = 255, nullable = false)
	private String surname;
	
	@Column(length = 255, nullable = false)
	private String email;
	
//	private Integer money -> assume a User has unlimited money?
	
	@Column(nullable = false, length = 255 )
	private String hashed_password;
	
	@Column(nullable = true, length = 255)
	private String address;
	
	@Column(nullable= true,length = 13)
	private String phoneNumber;
	
	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private UserRole role;
	
//	@Column(name = "session_cookie")
//	private String sessionCookie;
	
//	private String expirationCoookieDate;
	

	
}
