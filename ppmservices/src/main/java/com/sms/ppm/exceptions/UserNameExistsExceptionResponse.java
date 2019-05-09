package com.sms.ppm.exceptions;

public class UserNameExistsExceptionResponse {

	private String username;

	public UserNameExistsExceptionResponse(String username) {
		this.username = username;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}


	
}
