package com.sms.ppm.exceptions;

import lombok.Data;

@Data
public class InvalidLoginResponse {

	private String username = "Invalid username";
	private String password = "Invalid password";
			
}
