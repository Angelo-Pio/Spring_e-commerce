package com.eng.app.model;

import java.util.Set;

public enum UserRole {
	GUEST(0),
	AUTH(1),
	ADMIN(2);

	private Integer value;

	private UserRole(Integer value) {
		this.value = value;
	}

	
	
			
}
