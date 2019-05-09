package com.sms.ppm.security.payload;

import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class LoginRequest {

	@NotBlank(message = "Username required")
	private String username;
	@NotBlank(message = "Password required")
	private String password;
	
}
